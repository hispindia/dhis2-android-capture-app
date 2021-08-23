package org.medhis2yes.usescases.eventsWithoutRegistration.eventInitial;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.medhis2yes.Bindings.ValueTypeExtensionsKt;
import org.medhis2yes.R;
import org.medhis2yes.data.dagger.PerActivity;
import org.medhis2yes.data.forms.EventRepository;
import org.medhis2yes.data.forms.FormRepository;
import org.medhis2yes.data.forms.RulesRepository;
import org.medhis2yes.data.forms.dataentry.FormUiModelColorFactoryImpl;
import org.medhis2yes.data.forms.dataentry.RuleEngineRepository;
import org.medhis2yes.data.forms.dataentry.fields.FieldViewModelFactory;
import org.medhis2yes.data.forms.dataentry.fields.FieldViewModelFactoryImpl;
import org.medhis2yes.data.prefs.PreferenceProvider;
import org.medhis2yes.data.schedulers.SchedulerProvider;
import org.medhis2yes.form.ui.style.FormUiColorFactory;
import org.medhis2yes.usescases.eventsWithoutRegistration.eventCapture.EventFieldMapper;
import org.medhis2yes.usescases.eventsWithoutRegistration.eventCapture.EventRuleEngineRepository;
import org.medhis2yes.utils.RulesUtilsProvider;
import org.medhis2yes.utils.analytics.AnalyticsHelper;
import org.medhis2yes.utils.analytics.matomo.MatomoAnalyticsController;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class EventInitialModule {

    private final EventInitialContract.View view;
    private final String stageUid;
    @Nullable
    private String eventUid;
    private Context activityContext;

    public EventInitialModule(@NonNull EventInitialContract.View view,
                              @Nullable String eventUid,
                              String stageUid,
                              Context context) {
        this.view = view;
        this.eventUid = eventUid;
        this.stageUid = stageUid;
        this.activityContext = context;
    }

    @Provides
    @PerActivity
    EventInitialPresenter providesPresenter(@NonNull RulesUtilsProvider rulesUtilsProvider,
                                            @NonNull EventInitialRepository eventInitialRepository,
                                            @NonNull SchedulerProvider schedulerProvider,
                                            @NonNull PreferenceProvider preferenceProvider,
                                            @NonNull AnalyticsHelper analyticsHelper,
                                            @NonNull MatomoAnalyticsController matomoAnalyticsController,
                                            @NonNull EventFieldMapper eventFieldMapper) {
        return new EventInitialPresenter(
                view,
                rulesUtilsProvider,
                eventInitialRepository,
                schedulerProvider,
                preferenceProvider,
                analyticsHelper,
                matomoAnalyticsController,
                eventFieldMapper);
    }

    @Provides
    @PerActivity
    EventFieldMapper provideFieldMapper(Context context, FieldViewModelFactory fieldFactory) {
        return new EventFieldMapper(fieldFactory, context.getString(R.string.field_is_mandatory));
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
    FormRepository formRepository(@NonNull RulesRepository rulesRepository,
                                  @NonNull D2 d2) {
        return new EventRepository(rulesRepository, eventUid, d2);
    }

    @Provides
    RulesRepository rulesRepository(@NonNull D2 d2) {
        return new RulesRepository(d2);
    }

    @Provides
    @PerActivity
    EventInitialRepository eventDetailRepository(D2 d2,
                                                 @NonNull FieldViewModelFactory fieldViewModelFactory,
                                                 RuleEngineRepository ruleEngineRepository) {
        return new EventInitialRepositoryImpl(eventUid, stageUid, d2, fieldViewModelFactory, ruleEngineRepository);
    }

    @Provides
    @PerActivity
    RuleEngineRepository ruleEngineRepository(D2 d2, FormRepository formRepository) {
        return new EventRuleEngineRepository(d2, formRepository, eventUid);
    }
}
