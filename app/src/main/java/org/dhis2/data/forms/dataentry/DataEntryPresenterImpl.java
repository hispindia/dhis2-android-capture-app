package org.dhis2.data.forms.dataentry;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.sqlbrite2.BriteDatabase;

import org.dhis2.data.forms.dataentry.fields.FieldViewModel;
import org.dhis2.data.forms.dataentry.fields.edittext.EditTextViewModel;
import org.dhis2.data.metadata.MetadataRepository;
import org.dhis2.data.schedulers.SchedulerProvider;
import org.dhis2.data.user.UserRepository;
import org.dhis2.utils.CodeGenerator;
import org.dhis2.utils.Result;
import org.hisp.dhis.android.core.common.ValueType;
import org.hisp.dhis.android.core.organisationunit.OrganisationUnitModel;
import org.hisp.dhis.android.core.trackedentity.TrackedEntityAttributeValueModel;
import org.hisp.dhis.android.core.trackedentity.TrackedEntityInstance;
import org.hisp.dhis.android.core.trackedentity.TrackedEntityInstanceModel;
import org.hisp.dhis.rules.models.RuleAction;
import org.hisp.dhis.rules.models.RuleActionAssign;
import org.hisp.dhis.rules.models.RuleActionCreateEvent;
import org.hisp.dhis.rules.models.RuleActionDisplayText;
import org.hisp.dhis.rules.models.RuleActionErrorOnCompletion;
import org.hisp.dhis.rules.models.RuleActionHideField;
import org.hisp.dhis.rules.models.RuleActionHideSection;
import org.hisp.dhis.rules.models.RuleActionSetMandatoryField;
import org.hisp.dhis.rules.models.RuleActionShowError;
import org.hisp.dhis.rules.models.RuleActionShowWarning;
import org.hisp.dhis.rules.models.RuleActionWarningOnCompletion;
import org.hisp.dhis.rules.models.RuleEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@SuppressWarnings("PMD")
final public class DataEntryPresenterImpl implements DataEntryPresenter {

    public static final String HOUSEHOLD_PROGRAM = "BgTTdBNKHwc";
    public static final String HOUSE_NO_HM = "YFjB0zhySP6";
    public static final String HOUSE_NO_H = "ZQMF7taSAw8";

    public static final String TYPE_OF_HOUSE_ID = "dCer94znEuY";
    public static final String NAME_ID = "xalnzkNfD77";
    public static final String SERIES_ID = "UqhbFTbVeSD";
    public static final String FAMILY_MEMBER_UNIQUE_ID = "Dnm1mq6iq2d";


    public static final String FAMILY_UNIQUE_ID = "uHv60gjn2gp";
    public static final String HEAD_OF_FAMILY_NAME = "FML9pARILz5";


    public static final String TRACKER_ASSOSCIATE_ID = "YFjB0zhySP6";
    public static final String ANM_NAME = "yDCO4KM4WVA";
    public static final String LOCALITYNAME = "MV4wWoZBrJS";



    @NonNull
    private final CodeGenerator codeGenerator;

    @NonNull
    private final DataEntryStore dataEntryStore;

    @NonNull
    private final DataEntryRepository dataEntryRepository;

    @NonNull
    private final RuleEngineRepository ruleEngineRepository;

    @NonNull
    private final SchedulerProvider schedulerProvider;

    @NonNull
    private final MetadataRepository metadataRepository;
    @NonNull
    private final CompositeDisposable disposable;
    private DataEntryView dataEntryView;
    private HashMap<String, FieldViewModel> currentFieldViewModels;

    private UserRepository userRepository;
    private BriteDatabase briteDatabase;

    String orgunitCode = "";

    DataEntryPresenterImpl(@NonNull CodeGenerator codeGenerator,
                           @NonNull DataEntryStore dataEntryStore,
                           @NonNull DataEntryRepository dataEntryRepository,
                           @NonNull RuleEngineRepository ruleEngineRepository,
                           @NonNull SchedulerProvider schedulerProvider,
                           @NonNull MetadataRepository metadataRepository, @NonNull UserRepository userRepository, @NonNull BriteDatabase briteDatabase) {
        this.codeGenerator = codeGenerator;
        this.dataEntryStore = dataEntryStore;
        this.dataEntryRepository = dataEntryRepository;
        this.ruleEngineRepository = ruleEngineRepository;
        this.schedulerProvider = schedulerProvider;
        this.disposable = new CompositeDisposable();
        this.metadataRepository = metadataRepository;
        this.userRepository = userRepository;
        this.briteDatabase = briteDatabase;
    }

    @SuppressLint("RxSubscribeOnError")
    @Override
    public void onAttach(@NonNull DataEntryView dataEntryView) {

        this.dataEntryView = dataEntryView;
        Observable<List<FieldViewModel>> fieldsFlowable = dataEntryRepository.list();
        Flowable<Result<RuleEffect>> ruleEffectFlowable = ruleEngineRepository.calculate()
                .subscribeOn(schedulerProvider.computation()).onErrorReturn(throwable -> Result.failure(new Exception(throwable)));

        // Combining results of two repositories into a single stream.
        Flowable<List<FieldViewModel>> viewModelsFlowable = Flowable.zip(
                fieldsFlowable.toFlowable(BackpressureStrategy.LATEST), ruleEffectFlowable, this::applyEffects);

        disposable.add(viewModelsFlowable
                .subscribeOn(schedulerProvider.io())//check if computation does better than io
                .observeOn(schedulerProvider.ui())
                .subscribe(dataEntryView.showFields(),
                        Timber::d
                ));

        disposable.add(dataEntryView.rowActions().debounce(500, TimeUnit.MILLISECONDS) //TODO: Check debounce time
                .subscribeOn(schedulerProvider.ui())
                .observeOn(schedulerProvider.io())
                .switchMap(action ->
                        {
                            Timber.d("dataEntryRepository.save(uid=[%s], value=[%s])",
                                    action.id(), action.value());
                            return dataEntryStore.save(action.id(), action.value());
                        }
                ).subscribe(result -> Timber.d(result.toString()),
                        Timber::d)
        );

        disposable.add(
                dataEntryView.optionSetActions()
                        .flatMap(
                                data -> metadataRepository.searchOptions(data.val0(), data.val1()).toFlowable(BackpressureStrategy.LATEST)
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                dataEntryView::setListOptions,
                                Timber::e
                        ));

        disposable.add(userRepository.myOrgUnits()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(organisationUnitModels -> {
//                    if(organisationUnitModels.size()>0) this.orgunitCode = getOrgUnitCode(organisationUnitModels.get(0).uid());

                    //till we edit hte sdk to save the code use name instead

                    if(organisationUnitModels.size()>0) this.orgunitCode = organisationUnitModels.get(0).shortName();

                }));
        
        
    }

    private void save(String uid, String value, Boolean isAttribute) {
        CompositeDisposable saveDisposable = new CompositeDisposable();
        if (!uid.isEmpty())
            saveDisposable.add(
                    dataEntryStore.save(uid, value)
                            .subscribeOn(Schedulers.computation())
                            .observeOn(Schedulers.io())
                            .subscribe(
                                    data -> Log.d("SAVED_DATA", "DONE"),
                                    Timber::e,
                                    saveDisposable::clear
                            ));
    }

    @Override
    public void onDetach() {
        disposable.clear();
    }

    @NonNull
    @Override
    public Observable<List<OrganisationUnitModel>> getOrgUnits() {
        return dataEntryRepository.getOrgUnits();
    }

    
    @NonNull
    public Observable<List<TrackedEntityInstanceModel>> getTeis(){
        return metadataRepository.getTrackedEntityInstances(HOUSEHOLD_PROGRAM);
    }
    @NonNull
    private List<FieldViewModel> applyEffects(
            @NonNull List<FieldViewModel> viewModels,
            @NonNull Result<RuleEffect> calcResult) {
        if (calcResult.error() != null) {
            calcResult.error().printStackTrace();
            return viewModels;
        }

        Map<String, FieldViewModel> fieldViewModels = toMap(viewModels);
        applyRuleEffects(fieldViewModels, calcResult);

        if (this.currentFieldViewModels == null)
            this.currentFieldViewModels = new HashMap<>();
        this.currentFieldViewModels.putAll(fieldViewModels);

        return new ArrayList<>(fieldViewModels.values());
    }

    @NonNull
    private static Map<String, FieldViewModel> toMap(@NonNull List<FieldViewModel> fieldViewModels) {

        Map<String, FieldViewModel> map = new LinkedHashMap<>();
        for (FieldViewModel fieldViewModel : fieldViewModels) {
            map.put(fieldViewModel.uid(), fieldViewModel);
        }
        return map;
    }

    @Nonnull
    private String getOrgUnitCode(String orgUnitUid) {
        String ouCode = "";
        Cursor cursor = briteDatabase.query("SELECT code FROM OrganisationUnit WHERE uid = ? LIMIT 1", orgUnitUid);
        if (cursor != null && cursor.moveToFirst() && cursor.getString(0) != null) {
            ouCode = cursor.getString(0);
            cursor.close();
        }
        return ouCode;
    }

    @SuppressLint("RxSubscribeOnError")
    private void applyRuleEffects(Map<String, FieldViewModel> fieldViewModels, Result<RuleEffect> calcResult) {
        CompositeDisposable savedisposable = new CompositeDisposable();
        if(fieldViewModels.get(TRACKER_ASSOSCIATE_ID)!=null && fieldViewModels.get(TRACKER_ASSOSCIATE_ID).value()!=null){
            Observable<List<TrackedEntityAttributeValueModel>> teiAttributeValues = metadataRepository.getTEIAttributeValues(HOUSEHOLD_PROGRAM, fieldViewModels.get(TRACKER_ASSOSCIATE_ID).value());
            savedisposable.add(teiAttributeValues
                    .subscribeOn(Schedulers.io())
                    .subscribe((attributevalues) -> {
                        for(TrackedEntityAttributeValueModel model :attributevalues){
                            String val = model.value();
                            if(val==null) val = "";
                            switch (model.trackedEntityAttribute()){

                                case TYPE_OF_HOUSE_ID:
                                    if(fieldViewModels.get(TYPE_OF_HOUSE_ID)!=null && (fieldViewModels.get(TYPE_OF_HOUSE_ID).value()==null || !fieldViewModels.get(TYPE_OF_HOUSE_ID).value().equals(val))){
                                        fieldViewModels.put(TYPE_OF_HOUSE_ID,fieldViewModels.get(TYPE_OF_HOUSE_ID).withValue(val));
                                        save(TYPE_OF_HOUSE_ID,val,true);
                                    }
                                    break;

                                case ANM_NAME:
                                    if(fieldViewModels.get(ANM_NAME)!=null && (fieldViewModels.get(ANM_NAME).value()==null || !fieldViewModels.get(ANM_NAME).value().equals(val))){
                                        fieldViewModels.put(ANM_NAME,fieldViewModels.get(ANM_NAME).withValue(val));
                                        save(ANM_NAME,val,true);
                                    }
                                    break;

                                case LOCALITYNAME:
                                    if(fieldViewModels.get(LOCALITYNAME)!=null && (fieldViewModels.get(LOCALITYNAME).value()==null || !fieldViewModels.get(LOCALITYNAME).value().equals(val))){
                                        fieldViewModels.put(LOCALITYNAME,fieldViewModels.get(LOCALITYNAME).withValue(val));
                                        save(LOCALITYNAME,val,true);
                                    }
                                    break;

                                case HOUSE_NO_H:
                                    if(fieldViewModels.get(HOUSE_NO_H)!=null && (fieldViewModels.get(HOUSE_NO_H).value()==null && !fieldViewModels.get(HOUSE_NO_H).value().equals(val))){
                                        fieldViewModels.put(HOUSE_NO_HM,fieldViewModels.get(HOUSE_NO_H).withValue(val));
                                        save(HOUSE_NO_H,val,true);
                                    }
                                    break;
                            }
                        }
                    },Timber::e,savedisposable::clear));

        }

            //for household program
            //Facility/Houseno/Type of house/Head of family
            ///ryR4a3NGUvr/MKJov93FcdU/lBQjxyHY7VI/Zn7txDI3LPe
            //ZQMF7taSAw8/dCer94znEuY/

//        String delm = "/";
//        String familyuniqueid = orgunitCode;
//        String familymemberuniqueid= orgunitCode;
//
//        if(fieldViewModels.get("ZQMF7taSAw8")!=null && fieldViewModels.get("ZQMF7taSAw8").value() != null ){//houseno
//            familyuniqueid+=delm+fieldViewModels.get("ZQMF7taSAw8").value();
//            familymemberuniqueid+=delm+fieldViewModels.get("ZQMF7taSAw8").value();
//        }
//        if(fieldViewModels.get("dCer94znEuY")!=null && fieldViewModels.get("dCer94znEuY").value() != null){//typeofhouse
//            familyuniqueid+=delm+fieldViewModels.get("dCer94znEuY").value();
//            familymemberuniqueid+=delm+fieldViewModels.get("dCer94znEuY").value();
//        }
//        if(fieldViewModels.get("FML9pARILz5")!=null && fieldViewModels.get("FML9pARILz5").value() != null ){//name
//            familyuniqueid+=delm+fieldViewModels.get("FML9pARILz5").value();
//            familymemberuniqueid+=delm+fieldViewModels.get("FML9pARILz5").value();
//        }
////        if(fieldViewModels.get("Zn7txDI3LPe")!=null)familyuniqueid+=delm+fieldViewModels.get("Zn7txDI3LPe").value();
//
//
//        if(fieldViewModels.get("uHv60gjn2gp")!=null){
//            if(fieldViewModels.get("uHv60gjn2gp").value() ==null || !fieldViewModels.get("uHv60gjn2gp").value().equals(familyuniqueid)) {
//                fieldViewModels.put("uHv60gjn2gp", fieldViewModels.get("uHv60gjn2gp").withValue(familyuniqueid));
//                save("uHv60gjn2gp",familyuniqueid,true);
//            }
//        }
            //household family unique id ends


            //HOUSEHOLDEMEMBER PROGRAM AUTOGENERATE
            String delm = "/";
            String familyuniqueid = orgunitCode;
            String familymemberuniqueid= orgunitCode;

            if(fieldViewModels.get(HOUSE_NO_HM)!=null && fieldViewModels.get(HOUSE_NO_HM).value() != null ){//houseno
                familyuniqueid+=delm+fieldViewModels.get(HOUSE_NO_HM).value();
                familymemberuniqueid+=delm+fieldViewModels.get(HOUSE_NO_HM).value();
            }
            if(fieldViewModels.get(HOUSE_NO_H)!=null && fieldViewModels.get(HOUSE_NO_H).value() != null ){//houseno
                familyuniqueid+=delm+fieldViewModels.get(HOUSE_NO_H).value();
                familymemberuniqueid+=delm+fieldViewModels.get(HOUSE_NO_H).value();
            }
            if(fieldViewModels.get(TYPE_OF_HOUSE_ID)!=null && fieldViewModels.get(TYPE_OF_HOUSE_ID).value() != null){//typeofhouse
                familyuniqueid+=fieldViewModels.get(TYPE_OF_HOUSE_ID).value();
                familymemberuniqueid+=fieldViewModels.get(TYPE_OF_HOUSE_ID).value();
            }
            if(fieldViewModels.get(HEAD_OF_FAMILY_NAME)!=null && fieldViewModels.get(HEAD_OF_FAMILY_NAME).value() != null){//headoffamily
                familyuniqueid+=delm+fieldViewModels.get(HEAD_OF_FAMILY_NAME).value();
//            familymemberuniqueid+=fieldViewModels.get(TYPE_OF_HOUSE_ID).value();
            }

            if(fieldViewModels.get(NAME_ID)!=null && fieldViewModels.get(NAME_ID).value() != null ){//name
//            familyuniqueid+=delm+fieldViewModels.get(NAME_ID).value();
                familymemberuniqueid+=delm+fieldViewModels.get(NAME_ID).value();
            }
            if(fieldViewModels.get(SERIES_ID)!=null && fieldViewModels.get(SERIES_ID).value() != null ){//name
//            familyuniqueid+=delm+fieldViewModels.get("FML9pARILz5").value();
                familymemberuniqueid+=delm+fieldViewModels.get(SERIES_ID).value();
            }
//        if(fieldViewModels.get("Zn7txDI3LPe")!=null)familyuniqueid+=delm+fieldViewModels.get("Zn7txDI3LPe").value();


            if(fieldViewModels.get(FAMILY_MEMBER_UNIQUE_ID)!=null){
                if(fieldViewModels.get(FAMILY_MEMBER_UNIQUE_ID).value() ==null || !fieldViewModels.get(FAMILY_MEMBER_UNIQUE_ID).value().equals(familymemberuniqueid)) {
                    fieldViewModels.put(FAMILY_MEMBER_UNIQUE_ID, fieldViewModels.get(FAMILY_MEMBER_UNIQUE_ID).withValue(familymemberuniqueid));
                    save(FAMILY_MEMBER_UNIQUE_ID,familymemberuniqueid,true);
                }
            }
            if(fieldViewModels.get(FAMILY_UNIQUE_ID)!=null){
                if(fieldViewModels.get(FAMILY_UNIQUE_ID).value() ==null || !fieldViewModels.get(FAMILY_UNIQUE_ID).value().equals(familyuniqueid)) {
                    fieldViewModels.put(FAMILY_UNIQUE_ID, fieldViewModels.get(FAMILY_UNIQUE_ID).withValue(familyuniqueid));
                    save(FAMILY_UNIQUE_ID,familyuniqueid,true);
                }
            }









        for (RuleEffect ruleEffect : calcResult.items()) {
            RuleAction ruleAction = ruleEffect.ruleAction();
            if (ruleAction instanceof RuleActionShowWarning) {
                RuleActionShowWarning showWarning = (RuleActionShowWarning) ruleAction;
                FieldViewModel model = fieldViewModels.get(showWarning.field());

                if (model != null)
                    fieldViewModels.put(showWarning.field(),
                            model.withWarning(showWarning.content()));
                else
                    Log.d("PR_FIELD_ERROR", String.format("Field with uid %s is missing", showWarning.field()));

            } else if (ruleAction instanceof RuleActionShowError) {
                RuleActionShowError showError = (RuleActionShowError) ruleAction;
                FieldViewModel model = fieldViewModels.get(showError.field());

                if (model != null)
                    fieldViewModels.put(showError.field(),
                            model.withError(showError.content()));

            } else if (ruleAction instanceof RuleActionHideField) {
                RuleActionHideField hideField = (RuleActionHideField) ruleAction;
                fieldViewModels.remove(hideField.field());
                dataEntryStore.save(hideField.field(), null);
            } else if (ruleAction instanceof RuleActionDisplayText) {
                RuleActionDisplayText displayText = (RuleActionDisplayText) ruleAction;
                String uid = displayText.content();

                EditTextViewModel textViewModel = EditTextViewModel.create(uid,
                        displayText.content(), false, ruleEffect.data(), "Information", 1, ValueType.TEXT, null, false, null);

                if (this.currentFieldViewModels == null ||
                        !this.currentFieldViewModels.containsKey(uid)) {
                    fieldViewModels.put(uid, textViewModel);
                } else if (this.currentFieldViewModels.containsKey(uid) &&
                        !currentFieldViewModels.get(uid).value().equals(textViewModel.value())) {
                    fieldViewModels.put(uid, textViewModel);
                } else {

                }

            /*} else if (ruleAction instanceof RuleActionDisplayKeyValuePair) { TODO: 18/10/2018 disabled for now
                String uid = codeGenerator.generate();
                RuleActionDisplayKeyValuePair displayKeyValuePair = (RuleActionDisplayKeyValuePair) ruleAction;
                EditTextViewModel textViewModel = EditTextViewModel.create(uid,
                        displayKeyValuePair.content(), false, ruleEffect.data(), "Information", 1, ValueType.TEXT, null, false, null);
                fieldViewModels.put(uid, textViewModel);*/

            } else if (ruleAction instanceof RuleActionHideSection) {
                RuleActionHideSection hideSection = (RuleActionHideSection) ruleAction;
                dataEntryView.removeSection(hideSection.programStageSection());
            } else if (ruleAction instanceof RuleActionAssign) {
                RuleActionAssign assign = (RuleActionAssign) ruleAction;

                if (fieldViewModels.get(assign.field()) == null)
                    save(assign.field(), ruleEffect.data(), assign.isAttribute());
                else {
                    String value = fieldViewModels.get(assign.field()).value();

                    if (value == null || !value.equals(ruleEffect.data())) {
                        save(assign.field(), ruleEffect.data(), assign.isAttribute());
                    }

                    fieldViewModels.put(assign.field(), fieldViewModels.get(assign.field()).withValue(ruleEffect.data()));

                }


            } else if (ruleAction instanceof RuleActionCreateEvent) {
                RuleActionCreateEvent createEvent = (RuleActionCreateEvent) ruleAction;
                //TODO: CREATE event with data from createEvent
            } else if (ruleAction instanceof RuleActionSetMandatoryField) {
                RuleActionSetMandatoryField mandatoryField = (RuleActionSetMandatoryField) ruleAction;
                FieldViewModel model = fieldViewModels.get(mandatoryField.field());
                if (model != null)
                    fieldViewModels.put(mandatoryField.field(), model.setMandatory());
            } else if (ruleAction instanceof RuleActionWarningOnCompletion) {
                RuleActionWarningOnCompletion warningOnCompletion = (RuleActionWarningOnCompletion) ruleAction;
                dataEntryView.messageOnComplete(warningOnCompletion.content(), true);
            } else if (ruleAction instanceof RuleActionErrorOnCompletion) {
                RuleActionErrorOnCompletion errorOnCompletion = (RuleActionErrorOnCompletion) ruleAction;
                dataEntryView.messageOnComplete(errorOnCompletion.content(), false);
            }

            dataEntryView.removeSection(null);

        }
    }

    public MetadataRepository getMetadataRepository(){
        return metadataRepository;
    }
}
