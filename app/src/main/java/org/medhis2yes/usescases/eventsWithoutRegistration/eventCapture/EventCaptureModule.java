package org.medhis2yes.usescases.eventsWithoutRegistration.eventCapture;

import android.content.Context;

import androidx.annotation.NonNull;

import org.medhis2yes.Bindings.ValueTypeExtensionsKt;
import org.medhis2yes.R;
import org.medhis2yes.data.dagger.PerActivity;
import org.medhis2yes.data.dhislogic.DhisEnrollmentUtils;
import org.medhis2yes.data.forms.EventRepository;
import org.medhis2yes.data.forms.FormRepository;
import org.medhis2yes.data.forms.RulesRepository;
import org.medhis2yes.data.forms.dataentry.DataEntryStore;
import org.medhis2yes.data.forms.dataentry.FormUiModelColorFactoryImpl;
import org.medhis2yes.data.forms.dataentry.RuleEngineRepository;
import org.medhis2yes.data.forms.dataentry.ValueStore;
import org.medhis2yes.data.forms.dataentry.ValueStoreImpl;
import org.medhis2yes.data.forms.dataentry.fields.FieldViewModelFactory;
import org.medhis2yes.data.forms.dataentry.fields.FieldViewModelFactoryImpl;
import org.medhis2yes.data.prefs.PreferenceProvider;
import org.medhis2yes.data.schedulers.SchedulerProvider;
import org.medhis2yes.form.data.FormRepositoryPersistenceImpl;
import org.medhis2yes.form.model.RowAction;
import org.medhis2yes.form.ui.style.FormUiColorFactory;
import org.medhis2yes.utils.RulesUtilsProvider;
import org.medhis2yes.utils.resources.ResourceManager;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

@PerActivity
@Module
public class EventCaptureModule {

    private final String eventUid;
    private final EventCaptureContract.View view;
    private final Context activityContext;

    public EventCaptureModule(EventCaptureContract.View view, String eventUid, Context context) {
        this.view = view;
        this.eventUid = eventUid;
        this.activityContext = context;
    }

    @Provides
    @PerActivity
    EventCaptureContract.Presenter providePresenter(@NonNull EventCaptureContract.EventCaptureRepository eventCaptureRepository,
                                                    @NonNull RulesUtilsProvider ruleUtils,
                                                    @NonNull ValueStore valueStore,
                                                    SchedulerProvider schedulerProvider,
                                                    PreferenceProvider preferences,
                                                    GetNextVisibleSection getNextVisibleSection,
                                                    EventFieldMapper fieldMapper,
                                                    FlowableProcessor<RowAction> onFieldActionProcessor,
                                                    FieldViewModelFactory fieldFactory) {
        return new EventCapturePresenterImpl(view, eventUid, eventCaptureRepository, ruleUtils, valueStore, schedulerProvider,
                preferences, getNextVisibleSection, fieldMapper, onFieldActionProcessor, fieldFactory.sectionProcessor());
    }

    @Provides
    @PerActivity
    EventFieldMapper provideFieldMapper(Context context, FieldViewModelFactory fieldFactory) {
        return new EventFieldMapper(fieldFactory, context.getString(R.string.field_is_mandatory));
    }

    @Provides
    @PerActivity
    EventCaptureContract.EventCaptureRepository provideRepository(FieldViewModelFactory fieldFactory,
                                                                  RuleEngineRepository ruleEngineRepository,
                                                                  D2 d2,
                                                                  ResourceManager resourceManager
    ) {
        return new EventCaptureRepositoryImpl(fieldFactory, ruleEngineRepository, eventUid, d2, resourceManager);
    }

    @Provides
    @PerActivity
    FieldViewModelFactory fieldFactory(Context context, FormUiColorFactory colorFactory) {
        return new FieldViewModelFactoryImpl(ValueTypeExtensionsKt.valueTypeHintMap(context), false, colorFactory);
    }

    @Provides
    @PerActivity
    FormUiColorFactory provideFormUiColorFactory() {
        return new FormUiModelColorFactoryImpl(activityContext, true);
    }

    @Provides
    @PerActivity
    RulesRepository rulesRepository(@NonNull D2 d2) {
        return new RulesRepository(d2);
    }

    @Provides
    @PerActivity
    RuleEngineRepository ruleEngineRepository(D2 d2, FormRepository formRepository) {
        return new EventRuleEngineRepository(d2, formRepository, eventUid);
    }

    @Provides
    @PerActivity
    FormRepository formRepository(@NonNull RulesRepository rulesRepository,
                                  @NonNull D2 d2) {
        return new EventRepository(rulesRepository, eventUid, d2);
    }

    @Provides
    @PerActivity
    ValueStore valueStore(@NonNull D2 d2) {
        return new ValueStoreImpl(d2, eventUid, DataEntryStore.EntryMode.DE, new DhisEnrollmentUtils(d2));
    }

    @Provides
    @PerActivity
    GetNextVisibleSection getNextVisibleSection() {
        return new GetNextVisibleSection();
    }

    @Provides
    @PerActivity
    FlowableProcessor<RowAction> getProcessor() {
        return PublishProcessor.create();
    }

    @Provides
    @PerActivity
    org.medhis2yes.form.data.FormRepository provideEventsFormRepository(@NonNull D2 d2) {
        return new FormRepositoryPersistenceImpl(
                new ValueStoreImpl(
                        d2,
                        eventUid,
                        DataEntryStore.EntryMode.DE,
                        new DhisEnrollmentUtils(d2)
                )
        );
    }
}
