package org.dhis2.usescases.eventsWithoutRegistration.eventCapture;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.ObservableField;

import org.dhis2.R;
import org.dhis2.data.forms.FormSectionViewModel;
import org.dhis2.data.forms.dataentry.StoreResult;
import org.dhis2.data.forms.dataentry.ValueStore;
import org.dhis2.data.forms.dataentry.ValueStoreImpl;
import org.dhis2.data.forms.dataentry.fields.ActionType;
import org.dhis2.data.forms.dataentry.fields.FieldViewModel;
import org.dhis2.data.forms.dataentry.fields.RowAction;
import org.dhis2.data.forms.dataentry.fields.display.DisplayViewModel;
import org.dhis2.data.forms.dataentry.fields.edittext.EditTextViewModel;
import org.dhis2.data.forms.dataentry.fields.optionset.OptionSetViewModel;
import org.dhis2.data.forms.dataentry.fields.spinner.SpinnerViewModel;
import org.dhis2.data.forms.dataentry.fields.visualOptionSet.MatrixOptionSetModel;
import org.dhis2.data.prefs.Preference;
import org.dhis2.data.prefs.PreferenceProvider;
import org.dhis2.data.schedulers.SchedulerProvider;
import org.dhis2.data.tuples.Quartet;
import org.dhis2.utils.AuthorityException;
import org.dhis2.utils.DhisTextUtils;
import org.dhis2.utils.Result;
import org.dhis2.utils.RulesActionCallbacks;
import org.dhis2.utils.RulesUtilsProvider;
import org.hisp.dhis.android.core.common.Unit;
import org.hisp.dhis.android.core.common.ValueType;
import org.hisp.dhis.android.core.event.EventStatus;
import org.hisp.dhis.rules.models.RuleActionShowError;
import org.hisp.dhis.rules.models.RuleEffect;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subjects.BehaviorSubject;
import kotlin.Pair;
import timber.log.Timber;

@Singleton
public class EventCapturePresenterImpl implements EventCaptureContract.Presenter, RulesActionCallbacks {

    private final EventCaptureContract.EventCaptureRepository eventCaptureRepository;
    private final RulesUtilsProvider rulesUtils;
    private final String eventUid;
    private final PublishProcessor<Unit> progressProcessor;
    private final PublishProcessor<Unit> sectionAdjustProcessor;
    private final PublishProcessor<Unit> formAdjustProcessor;
    private final SchedulerProvider schedulerProvider;
    private final ValueStore valueStore;
    private final EventFieldMapper fieldMapper;
    private CompositeDisposable compositeDisposable;
    private EventCaptureContract.View view;
    private ObservableField<String> currentSection;
    private FlowableProcessor<Boolean> showCalculationProcessor;
    private List<FormSectionViewModel> sectionList;
    private Map<String, FieldViewModel> emptyMandatoryFields;
    //Rules data
    private Map<String, List<String>> optionsToHide = new HashMap<>();
    private Map<String, List<String>> optionsGroupsToHide = new HashMap<>();
    private Map<String, List<String>> optionsGroupToShow = new HashMap<>();
    private boolean canComplete;
    private String completeMessage;
    private Map<String, String> errors;
    private EventStatus eventStatus;
    private boolean hasExpired;
    private final Flowable<String> sectionProcessor;
    private ConnectableFlowable<List<FieldViewModel>> fieldFlowable;
    private PublishProcessor<Unit> notesCounterProcessor;
    private BehaviorSubject<List<FieldViewModel>> formFieldsProcessor;
    private boolean assignedValueChanged;
    private int calculationLoop = 0;
    private final int MAX_LOOP_CALCULATIONS = 5;
    private PreferenceProvider preferences;
    private GetNextVisibleSection getNextVisibleSection;
    private Pair<Boolean, Boolean> showErrors;
    private FlowableProcessor<RowAction> onFieldActionProcessor;


    public EventCapturePresenterImpl(EventCaptureContract.View view, String eventUid,
                                     EventCaptureContract.EventCaptureRepository eventCaptureRepository,
                                     RulesUtilsProvider rulesUtils,
                                     ValueStore valueStore, SchedulerProvider schedulerProvider,
                                     PreferenceProvider preferences,
                                     GetNextVisibleSection getNextVisibleSection,
                                     EventFieldMapper fieldMapper,
                                     FlowableProcessor<RowAction> onFieldActionProcessor, Flowable<String> sectionProcessor) {
        this.view = view;
        this.eventUid = eventUid;
        this.eventCaptureRepository = eventCaptureRepository;
        this.rulesUtils = rulesUtils;
        this.valueStore = valueStore;
        this.schedulerProvider = schedulerProvider;
        this.currentSection = new ObservableField<>("");
        this.errors = new HashMap<>();
        this.emptyMandatoryFields = new HashMap<>();
        this.canComplete = true;
        this.sectionList = new ArrayList<>();
        this.compositeDisposable = new CompositeDisposable();
        this.preferences = preferences;
        this.getNextVisibleSection = getNextVisibleSection;
        this.showErrors = new Pair<>(false, false);
        this.fieldMapper = fieldMapper;
        this.onFieldActionProcessor = onFieldActionProcessor;

        this.sectionProcessor = sectionProcessor;
        showCalculationProcessor = PublishProcessor.create();
        progressProcessor = PublishProcessor.create();
        sectionAdjustProcessor = PublishProcessor.create();
        formAdjustProcessor = PublishProcessor.create();
        notesCounterProcessor = PublishProcessor.create();
        formFieldsProcessor = BehaviorSubject.createDefault(new ArrayList<>());
    }

    @Override
    public void init() {

        compositeDisposable.add(
                eventCaptureRepository.eventIntegrityCheck()
                        .filter(check -> !check)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(
                                checkDidNotPass -> view.showEventIntegrityAlert(),
                                Timber::e
                        )
        );

        compositeDisposable.add(
                Flowable.zip(
                        eventCaptureRepository.programStageName(),
                        eventCaptureRepository.eventDate(),
                        eventCaptureRepository.orgUnit(),
                        eventCaptureRepository.catOption(),
                        Quartet::create
                )
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(
                                data -> {
                                    preferences.setValue(Preference.CURRENT_ORG_UNIT, data.val2().uid());
                                    view.renderInitialInfo(data.val0(), data.val1(), data.val2().displayName(), data.val3());
                                },
                                Timber::e
                        )

        );

        compositeDisposable.add(
                eventCaptureRepository.programStage()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(
                                view::setProgramStage,
                                Timber::e
                        )
        );


        compositeDisposable.add(
                eventCaptureRepository.eventStatus()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(
                                data -> {
                                    this.eventStatus = data;
                                    checkExpiration();
                                },
                                Timber::e
                        )
        );

        compositeDisposable.add(
                eventCaptureRepository.eventSections()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(
                                data -> {
                                    this.sectionList = data;
                                },
                                Timber::e
                        )
        );

        compositeDisposable.add(
                Flowable.zip(
                        sectionAdjustProcessor.onBackpressureBuffer(),
                        formAdjustProcessor.onBackpressureBuffer(),
                        (a, b) -> new Unit())
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.io())
                        .subscribe(
                                unit -> {
                                    showCalculationProcessor.onNext(false);
                                    progressProcessor.onNext(new Unit());
                                },
                                Timber::e
                        )
        );

        fieldFlowable = getFieldFlowable();

        compositeDisposable.add(
                eventCaptureRepository.eventSections()
                        .flatMap(sectionList ->
                                sectionProcessor.startWith(sectionList.get(0).sectionUid())
                                        .observeOn(schedulerProvider.io())
                                        .switchMap(section ->
                                                fieldFlowable.map(fields ->
                                                        fieldMapper.map(
                                                                fields,
                                                                sectionList,
                                                                section,
                                                                errors,
                                                                emptyMandatoryFields,
                                                                showErrors
                                                        ))))
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(sectionsAndFields -> {
                                    if (showErrors.component1() || showErrors.component2()) {
                                        qualityCheck();
                                    }
                                    if (assignedValueChanged && errors.isEmpty() && calculationLoop < MAX_LOOP_CALCULATIONS) {
                                        calculationLoop++;
                                        nextCalculation(true);
                                    } else {
                                        if (calculationLoop == 5) {
                                            view.showLoopWarning();
                                        }
                                        calculationLoop = 0;
                                        formFieldsProcessor.onNext(sectionsAndFields.component2());
                                        formAdjustProcessor.onNext(new Unit());

                                        view.updatePercentage(
                                                fieldMapper.completedFieldsPercentage(),
                                                fieldMapper.unsupportedFieldsPercentage()
                                        );
                                    }
                                },
                                Timber::e
                        ));

        compositeDisposable.add(
                sectionProcessor
                        .observeOn(schedulerProvider.io())
                        .subscribeOn(schedulerProvider.ui())
                        .subscribe(
                                data -> {
                                    view.showProgress();
                                    currentSection.set(data);
                                    showCalculationProcessor.onNext(true);
                                },
                                Timber::e
                        )
        );

        fieldFlowable.connect();

        compositeDisposable.add(
                onFieldActionProcessor.subscribe(
                        rowAction -> {
                            if (rowAction.getType() == ActionType.ON_FOCUS) {
                                view.hideNavigationBar();
                            }
                        },
                        Timber::e
                )
        );
    }

    @VisibleForTesting
    public String getFieldSection(FieldViewModel fieldViewModel) {
        String fieldSection;
        if (fieldViewModel instanceof DisplayViewModel) {
            fieldSection = "display";
        } else {
            fieldSection = fieldViewModel.programStageSection() != null ?
                    fieldViewModel.programStageSection() :
                    "";
        }
        return fieldSection;
    }

    @Override
    public BehaviorSubject<List<FieldViewModel>> formFieldsFlowable() {
        return formFieldsProcessor;
    }

    private ConnectableFlowable<List<FieldViewModel>> getFieldFlowable() {
        return showCalculationProcessor
                .startWith(true)
                .filter(newCalculation -> newCalculation)
                .observeOn(schedulerProvider.io())
                .switchMap(newCalculation -> Flowable.zip(
                        eventCaptureRepository.list(onFieldActionProcessor),
                        eventCaptureRepository.calculate(),
                        this::applyEffects)
                ).map(fields ->
                        {
                            emptyMandatoryFields = new HashMap<>();
                            for (FieldViewModel fieldViewModel : fields) {
                                if (fieldViewModel.mandatory() && DhisTextUtils.Companion.isEmpty(fieldViewModel.value())) {
                                    emptyMandatoryFields.put(fieldViewModel.uid(), fieldViewModel);
                                }
                            }
                            if (!fields.isEmpty()) {
                                int lastIndex = fields.size() - 1;
                                FieldViewModel field = fields.get(lastIndex);
                                if (field instanceof EditTextViewModel &&
                                        ((EditTextViewModel) field).valueType() != ValueType.LONG_TEXT
                                ) {
                                    fields.set(lastIndex, ((EditTextViewModel) field).withKeyBoardActionDone());
                                }
                            }
                            return fields;
                        }
                )
                .publish();

    }

    private void checkExpiration() {
        if (eventStatus == EventStatus.COMPLETED)
            compositeDisposable.add(
                    eventCaptureRepository.isCompletedEventExpired(eventUid)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .subscribe(
                                    hasExpiredResult -> this.hasExpired = hasExpiredResult && !eventCaptureRepository.isEventEditable(eventUid),
                                    Timber::e
                            )
            );
        else
            this.hasExpired = !eventCaptureRepository.isEventEditable(eventUid);
    }

    @Override
    public void onBackClick() {
        view.goBack();
    }

    @Override
    public void nextCalculation(boolean doNextCalculation) {
        showCalculationProcessor.onNext(doNextCalculation);
    }

    @NonNull
    private synchronized List<FieldViewModel> applyEffects(
            @NonNull List<FieldViewModel> viewModels,
            @NonNull Result<RuleEffect> calcResult) {

        if (calcResult.error() != null) {
            Timber.e(calcResult.error());
            return viewModels;
        }

        //Reset effectsT
        assignedValueChanged = false;
        optionsToHide.clear();
        optionsGroupsToHide.clear();
        optionsGroupToShow.clear();
        errors.clear();
        completeMessage = null;
        canComplete = true;

        Map<String, FieldViewModel> fieldViewModels = toMap(viewModels);
        rulesUtils.applyRuleEffects(fieldViewModels, calcResult, this);

        //Set/remove for HIDEOPTION/HIDEOPTIONGROUP/SHOWOPTIONGROUP
        ArrayList<FieldViewModel> fieldList = new ArrayList<>(fieldViewModels.values());
        ListIterator<FieldViewModel> fieldIterator = fieldList.listIterator();
        while (fieldIterator.hasNext()) {
            FieldViewModel field = fieldIterator.next();
            if (field instanceof MatrixOptionSetModel) {
                FieldViewModel hiddenMatrixModel = ((MatrixOptionSetModel) field).setOptionsToHide(
                        optionsToHide.get(field.uid()) != null ? optionsToHide.get(field.uid()) : new ArrayList<>(),
                        eventCaptureRepository.getOptionsFromGroups(
                                optionsGroupsToHide.get(field.uid()) != null ? optionsGroupsToHide.get(field.uid()) : new ArrayList<>()
                        ),
                        eventCaptureRepository.getOptionsFromGroups(
                                optionsGroupToShow.get(field.uid()) != null ? optionsGroupToShow.get(field.uid()) : new ArrayList<>()
                        )
                );
                fieldIterator.set(hiddenMatrixModel);
            } else if (field instanceof SpinnerViewModel) {
                FieldViewModel hiddenSpinnerModel = ((SpinnerViewModel) field).setOptionsToHide(
                        optionsToHide.get(field.uid()) != null ? optionsToHide.get(field.uid()) : new ArrayList<>(),
                        optionsGroupsToHide.get(field.uid()) != null ? optionsGroupsToHide.get(field.uid()) : new ArrayList<>()
                );
                fieldIterator.set(hiddenSpinnerModel);
                if (optionsGroupToShow.keySet().contains(field.uid())) {
                    FieldViewModel showSpinnerModel = ((SpinnerViewModel) hiddenSpinnerModel).setOptionGroupsToShow(
                            optionsGroupToShow.get(field.uid()) != null ? optionsGroupToShow.get(field.uid()) : new ArrayList<>()
                    );
                    fieldIterator.set(showSpinnerModel);
                }
            } else if (field instanceof OptionSetViewModel) {
                FieldViewModel hiddenOptionSetModel = ((OptionSetViewModel) field).setOptionsToHide(
                        optionsToHide.get(field.uid()) != null ? optionsToHide.get(field.uid()) : new ArrayList<>()
                );
                fieldIterator.set(hiddenOptionSetModel);
                if (optionsGroupToShow.keySet().contains(field.uid())) {
                    FieldViewModel showOptionSetModel = ((OptionSetViewModel) hiddenOptionSetModel).setOptionsToShow(
                            eventCaptureRepository.getOptionsFromGroups(
                                    optionsGroupToShow.get(field.uid()) != null ? optionsGroupToShow.get(field.uid()) : new ArrayList<>()
                            )
                    );
                    fieldIterator.set(showOptionSetModel);
                }
            }
        }

        return fieldList;
    }

    @NonNull
    private static Map<String, FieldViewModel> toMap(@NonNull List<FieldViewModel> fieldViewModels) {
        Map<String, FieldViewModel> map = new LinkedHashMap<>();
        for (FieldViewModel fieldViewModel : fieldViewModels) {
            map.put(fieldViewModel.uid(), fieldViewModel);
        }
        return map;
    }

    @Override
    public void attempFinish() {

        qualityCheck();

        if (!errors.isEmpty() && errors.get(currentSection.get()) != null) {
            view.showErrorSnackBar();
        }

        if (eventStatus != EventStatus.ACTIVE) {
            setUpActionByStatus(eventStatus);
        } else {
            view.showCompleteActions(canComplete && eventCaptureRepository.isEnrollmentOpen(), completeMessage, errors, emptyMandatoryFields);
        }

        view.showNavigationBar();
    }

    private void setUpActionByStatus(EventStatus eventStatus) {
        switch (eventStatus) {
            case COMPLETED:
                if (!hasExpired && !eventCaptureRepository.isEnrollmentCancelled())
                    view.attemptToReopen();
                else
                    view.finishDataEntry();
                break;
            case OVERDUE:
                view.attemptToSkip();
                break;
            case SKIPPED:
                view.attemptToReschedule();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isEnrollmentOpen() {
        return eventCaptureRepository.isEnrollmentOpen();
    }

    @Override
    public void goToSection() {

    }

    @Override
    public void completeEvent(boolean addNew) {
        compositeDisposable.add(
                eventCaptureRepository.completeEvent()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(
                                success -> {
                                    if (addNew)
                                        view.restartDataEntry();
                                    else
                                        view.finishDataEntry();
                                },
                                Timber::e
                        ));
    }

    @Override
    public void reopenEvent() {
        compositeDisposable.add(
                eventCaptureRepository.canReOpenEvent()
                        .flatMap(canReOpen -> {
                            if (canReOpen)
                                return Single.just(true);
                            else
                                return Single.error(new AuthorityException(view.getContext().getString(R.string.uncomplete_authority_error)));
                        })
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(canReOpenEvent -> {
                                    if (canReOpenEvent) {
                                        if (eventCaptureRepository.reopenEvent()) {
                                            view.showSnackBar(R.string.event_reopened);
                                            eventStatus = EventStatus.ACTIVE;
                                        }
                                    }
                                },
                                error -> {
                                    if (error instanceof AuthorityException)
                                        view.displayMessage(error.getMessage());
                                    else
                                        Timber.e(error);
                                }
                        ));
    }

    @Override
    public void deleteEvent() {
        compositeDisposable.add(eventCaptureRepository.deleteEvent()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        result -> {
                            if (result)
                                view.showSnackBar(R.string.event_was_deleted);
                        },
                        Timber::e,
                        () -> view.finishDataEntry()
                )
        );
    }

    @Override
    public void skipEvent() {
        compositeDisposable.add(eventCaptureRepository.updateEventStatus(EventStatus.SKIPPED)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        result -> view.showSnackBar(R.string.event_was_skipped),
                        Timber::e,
                        () -> view.finishDataEntry()
                )
        );
    }

    @Override
    public void rescheduleEvent(Date time) {
        compositeDisposable.add(
                eventCaptureRepository.rescheduleEvent(time)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(
                                result -> view.finishDataEntry(),
                                Timber::e
                        )
        );
    }

    @Override
    public boolean canWrite() {
        return eventCaptureRepository.getAccessDataWrite();
    }

    @Override
    public boolean hasExpired() {
        return hasExpired;
    }

    @Override
    public void onDettach() {
        this.compositeDisposable.clear();
    }

    @Override
    public void displayMessage(String message) {
        view.displayMessage(message);
    }

    @SuppressLint("CheckResult")
    @Override
    public void saveImage(String uuid, String filePath) {
        valueStore.save(uuid, filePath).blockingFirst();
    }

    //region ruleActions

    @Override
    public void setShowError(@NonNull RuleActionShowError showError, @Nullable FieldViewModel model) {
        canComplete = false;
        errors.put(eventCaptureRepository.getSectionFor(showError.field()), showError.field());
    }

    @Override
    public void unsupportedRuleAction() {
        Timber.d(view.getContext().getString(R.string.unsupported_program_rule));
    }

    @SuppressLint("CheckResult")
    @Override
    public void save(@NotNull @NonNull String uid, @Nullable String value) {
        StoreResult result = valueStore.saveWithTypeCheck(uid, value).blockingFirst();
        if (result.component2() == ValueStoreImpl.ValueStoreResult.VALUE_CHANGED) {
            assignedValueChanged = true;
            setValueChanged(uid);
        }
    }

    @Override
    public void setMessageOnComplete(@NonNull String message, boolean canComplete) {
        this.canComplete = canComplete;
        this.completeMessage = message;
    }

    @Override
    public void setHideProgramStage(@NonNull String programStageUid) {
        //do not apply
    }

    @Override
    public void setOptionToHide(@NonNull String optionUid, @NonNull String field) {
        if (!optionsToHide.containsKey(field)) {
            optionsToHide.put(field, new ArrayList<>());
        }
        optionsToHide.get(field).add(optionUid);
        StoreResult result = valueStore.deleteOptionValueIfSelected(field, optionUid);
        if (result.component2() == ValueStoreImpl.ValueStoreResult.VALUE_CHANGED) {
            assignedValueChanged = true;
            setValueChanged(field);
        }
    }

    @Override
    public void setOptionGroupToHide(@NonNull String optionGroupUid, boolean toHide, @NonNull String field) {
        if (toHide) {
            if (!optionsGroupsToHide.containsKey(field)) {
                optionsGroupsToHide.put(field, new ArrayList<>());
            }
            optionsGroupsToHide.get(field).add(optionGroupUid);
            if (!optionsToHide.containsKey(field)) {
                optionsToHide.put(field, new ArrayList<>());
            }
            optionsToHide.get(field).addAll(eventCaptureRepository.getOptionsFromGroups(Collections.singletonList(optionGroupUid)));
            StoreResult result = valueStore.deleteOptionValueIfSelectedInGroup(field, optionGroupUid, true);
            if (result.component2() == ValueStoreImpl.ValueStoreResult.VALUE_CHANGED) {
                assignedValueChanged = true;
                setValueChanged(field);
            }
        } else if (!optionsGroupsToHide.containsKey(field) || !optionsGroupsToHide.get(field).contains(optionGroupUid)) {
            if (optionsGroupToShow.get(field) != null) {
                optionsGroupToShow.get(field).add(optionGroupUid);
            } else {
                optionsGroupToShow.put(field, Collections.singletonList(optionGroupUid));
            }
            StoreResult result = valueStore.deleteOptionValueIfSelectedInGroup(field, optionGroupUid, false);
            if (result.component2() == ValueStoreImpl.ValueStoreResult.VALUE_CHANGED) {
                assignedValueChanged = true;
                setValueChanged(field);
            }
        }
    }

    //endregion

    @Override
    public void initNoteCounter() {
        if (!notesCounterProcessor.hasSubscribers()) {
            compositeDisposable.add(
                    notesCounterProcessor.startWith(new Unit())
                            .flatMapSingle(unit ->
                                    eventCaptureRepository.getNoteCount())
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .subscribe(
                                    numberOfNotes ->
                                            view.updateNoteBadge(numberOfNotes),
                                    Timber::e
                            )
            );
        } else {
            notesCounterProcessor.onNext(new Unit());
        }
    }

    @Override
    public void refreshTabCounters() {
        initNoteCounter();
    }

    @Override
    public void hideProgress() {
        view.hideProgress();
    }

    @Override
    public void showProgress() {
        view.showProgress();
    }

    private void qualityCheck() {
        Pair<Boolean, Boolean> currentShowError = showErrors;
        showErrors = new Pair<>(!emptyMandatoryFields.isEmpty(), !errors.isEmpty());
        showCalculationProcessor.onNext(
                currentShowError.getFirst() != showErrors.getFirst() ||
                        currentShowError.getSecond() != showErrors.getSecond()
        );
    }

    @Override
    public boolean getCompletionPercentageVisibility() {
        return eventCaptureRepository.showCompletionPercentage();
    }

    @Override
    public void setValueChanged(@NotNull String uid) {
        compositeDisposable.add(
                Completable.fromCallable(() -> {
                    eventCaptureRepository.updateFieldValue(uid);
                    return true;
                })
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.io())
                        .subscribe(() -> {
                        }, Timber::d)
        );
    }
}
