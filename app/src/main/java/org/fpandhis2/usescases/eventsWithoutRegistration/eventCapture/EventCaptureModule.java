package org.fpandhis2.usescases.eventsWithoutRegistration.eventCapture;

import android.content.Context;

import androidx.annotation.NonNull;

import org.fpandhis2.Bindings.ValueTypeExtensionsKt;
import org.fpandhis2.R;
import org.fpandhis2.data.dagger.PerActivity;
import org.fpandhis2.data.dhislogic.DhisEventUtils;
import org.fpandhis2.data.forms.EventRepository;
import org.fpandhis2.data.forms.FormRepository;
import org.fpandhis2.data.forms.RulesRepository;
import org.fpandhis2.data.forms.dataentry.DataEntryStore;
import org.fpandhis2.data.forms.dataentry.ValueStore;
import org.fpandhis2.data.forms.dataentry.ValueStoreImpl;
import org.fpandhis2.data.forms.dataentry.fields.FieldViewModelFactory;
import org.fpandhis2.data.forms.dataentry.fields.FieldViewModelFactoryImpl;
import org.fpandhis2.data.prefs.PreferenceProvider;
import org.fpandhis2.data.schedulers.SchedulerProvider;
import org.fpandhis2.utils.RulesUtilsProvider;
import org.hisp.dhis.android.core.D2;
import org.hisp.dhis.rules.RuleExpressionEvaluator;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class EventCaptureModule {


    private final String eventUid;
    private final EventCaptureContract.View view;

    public EventCaptureModule(EventCaptureContract.View view, String eventUid) {
        this.view = view;
        this.eventUid = eventUid;
    }

    @Provides
    @PerActivity
    EventCaptureContract.Presenter providePresenter(@NonNull EventCaptureContract.EventCaptureRepository eventCaptureRepository,
                                                    @NonNull RulesUtilsProvider ruleUtils,
                                                    @NonNull ValueStore valueStore,
                                                    SchedulerProvider schedulerProvider,
                                                    PreferenceProvider preferences,
                                                    GetNextVisibleSection getNextVisibleSection,
                                                    EventFieldMapper fieldMapper) {
        return new EventCapturePresenterImpl(view, eventUid, eventCaptureRepository, ruleUtils, valueStore, schedulerProvider,
                preferences, getNextVisibleSection, fieldMapper);
    }

    @Provides
    @PerActivity
    EventFieldMapper provideFieldMapper(Context context){
        return new EventFieldMapper(context.getString(R.string.field_is_mandatory));
    }

    @Provides
    @PerActivity
    EventCaptureContract.EventCaptureRepository provideRepository(Context context,
                                                                  FormRepository formRepository,
                                                                  D2 d2,
                                                                  DhisEventUtils eventUtils) {
        FieldViewModelFactory fieldFactory = new FieldViewModelFactoryImpl(ValueTypeExtensionsKt.valueTypeHintMap(context));
        return new EventCaptureRepositoryImpl(fieldFactory, formRepository, eventUid, d2, eventUtils);
    }

    @Provides
    @PerActivity
    RulesRepository rulesRepository(@NonNull D2 d2) {
        return new RulesRepository(d2);
    }

    @Provides
    @PerActivity
    FormRepository formRepository(@NonNull RuleExpressionEvaluator evaluator,
                                  @NonNull RulesRepository rulesRepository,
                                  @NonNull D2 d2) {
        return new EventRepository(evaluator, rulesRepository, eventUid, d2);
    }

    @Provides
    @PerActivity
    ValueStore valueStore(@NonNull D2 d2) {
        return new ValueStoreImpl(d2, eventUid, DataEntryStore.EntryMode.DE);
    }

    @Provides
    @PerActivity
    GetNextVisibleSection getNextVisibleSection() {
        return new GetNextVisibleSection();
    }
}
