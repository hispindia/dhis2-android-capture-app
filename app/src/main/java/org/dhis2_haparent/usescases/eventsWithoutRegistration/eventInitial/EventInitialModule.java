package org.dhis2_haparent.usescases.eventsWithoutRegistration.eventInitial;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.dhis2_haparent.R;
import org.dhis2_haparent.commons.di.dagger.PerActivity;
import org.dhis2_haparent.commons.prefs.PreferenceProvider;
import org.dhis2_haparent.commons.resources.ResourceManager;
import org.dhis2_haparent.commons.schedulers.SchedulerProvider;
import org.dhis2_haparent.data.forms.EventRepository;
import org.dhis2_haparent.data.forms.FormRepository;
import org.dhis2_haparent.form.data.RulesRepository;
import org.dhis2_haparent.form.data.metadata.OptionSetConfiguration;
import org.dhis2_haparent.form.data.metadata.OrgUnitConfiguration;
import org.dhis2_haparent.form.ui.provider.LegendValueProviderImpl;
import org.dhis2_haparent.form.ui.style.FormUiModelColorFactoryImpl;
import org.dhis2_haparent.data.forms.dataentry.RuleEngineRepository;
import org.dhis2_haparent.form.data.RulesUtilsProvider;
import org.dhis2_haparent.form.ui.FieldViewModelFactory;
import org.dhis2_haparent.form.ui.FieldViewModelFactoryImpl;
import org.dhis2_haparent.form.ui.LayoutProviderImpl;
import org.dhis2_haparent.form.ui.provider.DisplayNameProviderImpl;
import org.dhis2_haparent.form.ui.provider.HintProviderImpl;
import org.dhis2_haparent.form.ui.provider.KeyboardActionProviderImpl;
import org.dhis2_haparent.form.ui.provider.UiEventTypesProviderImpl;
import org.dhis2_haparent.form.ui.provider.UiStyleProviderImpl;
import org.dhis2_haparent.form.ui.style.LongTextUiColorFactoryImpl;
import org.dhis2_haparent.usescases.eventsWithoutRegistration.eventCapture.EventFieldMapper;
import org.dhis2_haparent.usescases.eventsWithoutRegistration.eventCapture.EventRuleEngineRepository;
import org.dhis2_haparent.utils.analytics.AnalyticsHelper;
import org.dhis2_haparent.commons.matomo.MatomoAnalyticsController;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

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
    FieldViewModelFactory fieldFactory(Context context, D2 d2, ResourceManager resourceManager) {
        return new FieldViewModelFactoryImpl(
                false,
                new UiStyleProviderImpl(
                        new FormUiModelColorFactoryImpl(activityContext, true),
                        new LongTextUiColorFactoryImpl(activityContext, true)
                ),
                new LayoutProviderImpl(),
                new HintProviderImpl(context),
                new DisplayNameProviderImpl(
                        new OptionSetConfiguration(d2),
                        new OrgUnitConfiguration(d2)
                ),
                new UiEventTypesProviderImpl(),
                new KeyboardActionProviderImpl(),
                new LegendValueProviderImpl(d2, resourceManager)
        );
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
