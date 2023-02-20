package org.dhis2_haparent.usescases.eventsWithoutRegistration.eventCapture;

import android.content.Context;

import androidx.annotation.NonNull;

import org.dhis2_haparent.R;
import org.dhis2_haparent.commons.data.EntryMode;
import org.dhis2_haparent.commons.di.dagger.PerActivity;
import org.dhis2_haparent.commons.network.NetworkUtils;
import org.dhis2_haparent.commons.prefs.PreferenceProvider;
import org.dhis2_haparent.commons.reporting.CrashReportController;
import org.dhis2_haparent.commons.reporting.CrashReportControllerImpl;
import org.dhis2_haparent.commons.resources.ResourceManager;
import org.dhis2_haparent.commons.schedulers.SchedulerProvider;
import org.dhis2_haparent.data.dhislogic.DhisEnrollmentUtils;
import org.dhis2_haparent.data.forms.EventRepository;
import org.dhis2_haparent.data.forms.FormRepository;
import org.dhis2_haparent.data.forms.dataentry.RuleEngineRepository;
import org.dhis2_haparent.data.forms.dataentry.SearchTEIRepository;
import org.dhis2_haparent.data.forms.dataentry.SearchTEIRepositoryImpl;
import org.dhis2_haparent.form.data.FormValueStore;
import org.dhis2_haparent.form.data.RulesRepository;
import org.dhis2_haparent.form.model.RowAction;
import org.dhis2_haparent.form.ui.FieldViewModelFactory;
import org.dhis2_haparent.usescases.eventsWithoutRegistration.eventCapture.domain.ConfigureEventCompletionDialog;
import org.dhis2_haparent.usescases.eventsWithoutRegistration.eventCapture.provider.EventCaptureResourcesProvider;
import org.dhis2_haparent.utils.customviews.navigationbar.NavigationPageConfigurator;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

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
                                                    SchedulerProvider schedulerProvider,
                                                    PreferenceProvider preferences,
                                                    ConfigureEventCompletionDialog configureEventCompletionDialog) {
        return new EventCapturePresenterImpl(
                view,
                eventUid,
                eventCaptureRepository,
                schedulerProvider,
                preferences,
                configureEventCompletionDialog);
    }

    @Provides
    @PerActivity
    EventFieldMapper provideFieldMapper(Context context, FieldViewModelFactory fieldFactory) {
        return new EventFieldMapper(fieldFactory, context.getString(R.string.field_is_mandatory));
    }

    @Provides
    @PerActivity
    EventCaptureContract.EventCaptureRepository provideRepository(D2 d2) {
        return new EventCaptureRepositoryImpl(eventUid, d2);
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
    FormValueStore valueStore(
            @NonNull D2 d2,
            CrashReportController crashReportController,
            NetworkUtils networkUtils,
            ResourceManager resourceManager
    ) {
        return new FormValueStore(
                d2,
                eventUid,
                EntryMode.DE,
                null,
                crashReportController,
                networkUtils,
                resourceManager
        );
    }

    @Provides
    @PerActivity
    SearchTEIRepository searchTEIRepository(D2 d2) {
        return new SearchTEIRepositoryImpl(d2, new DhisEnrollmentUtils(d2), new CrashReportControllerImpl());
    }

    @Provides
    @PerActivity
    FlowableProcessor<RowAction> getProcessor() {
        return PublishProcessor.create();
    }

    @Provides
    @PerActivity
    NavigationPageConfigurator pageConfigurator(
            EventCaptureContract.EventCaptureRepository repository
    ) {
        return new EventPageConfigurator(repository);
    }

    @Provides
    @PerActivity
    ConfigureEventCompletionDialog provideConfigureEventCompletionDialog(
            EventCaptureResourcesProvider eventCaptureResourcesProvider
    ) {
        return new ConfigureEventCompletionDialog(eventCaptureResourcesProvider);
    }

    @Provides
    @PerActivity
    EventCaptureResourcesProvider provideEventCaptureResourcesProvider(
            ResourceManager resourceManager
    ) {
        return new EventCaptureResourcesProvider(resourceManager);
    }
}
