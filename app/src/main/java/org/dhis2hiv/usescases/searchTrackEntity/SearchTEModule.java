package org.dhis2hiv.usescases.searchTrackEntity;

import android.content.Context;

import androidx.annotation.NonNull;

import org.dhis2hiv.R;
import org.dhis2hiv.animations.CarouselViewAnimations;
import org.dhis2hiv.data.dagger.PerActivity;
import org.dhis2hiv.data.enrollment.EnrollmentUiDataHelper;
import org.dhis2hiv.data.filter.FilterPresenter;
import org.dhis2hiv.data.prefs.PreferenceProvider;
import org.dhis2hiv.data.schedulers.SchedulerProvider;
import org.dhis2hiv.data.sorting.SearchSortingValueSetter;
import org.dhis2hiv.uicomponents.map.geometry.bound.BoundsGeometry;
import org.dhis2hiv.uicomponents.map.geometry.bound.GetBoundingBox;
import org.dhis2hiv.uicomponents.map.geometry.line.MapLineRelationshipToFeature;
import org.dhis2hiv.uicomponents.map.geometry.mapper.featurecollection.MapRelationshipsToFeatureCollection;
import org.dhis2hiv.uicomponents.map.geometry.mapper.featurecollection.MapTeiEventsToFeatureCollection;
import org.dhis2hiv.uicomponents.map.geometry.mapper.featurecollection.MapTeisToFeatureCollection;
import org.dhis2hiv.uicomponents.map.geometry.point.MapPointToFeature;
import org.dhis2hiv.uicomponents.map.geometry.polygon.MapPolygonPointToFeature;
import org.dhis2hiv.uicomponents.map.geometry.polygon.MapPolygonToFeature;
import org.dhis2hiv.uicomponents.map.mapper.EventToEventUiComponent;
import org.dhis2hiv.uicomponents.map.mapper.MapRelationshipToRelationshipMapModel;
import org.dhis2hiv.utils.DateUtils;
import org.dhis2hiv.utils.analytics.AnalyticsHelper;
import org.dhis2hiv.utils.filters.FiltersAdapter;
import org.dhis2hiv.utils.resources.ResourceManager;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class SearchTEModule {

    private final SearchTEContractsModule.View view;
    private final String teiType;
    private final String initialProgram;

    public SearchTEModule(SearchTEContractsModule.View view,
                          String tEType,
                          String initialProgram) {
        this.view = view;
        this.teiType = tEType;
        this.initialProgram = initialProgram;
    }

    @Provides
    @PerActivity
    SearchTEContractsModule.View provideView(SearchTEActivity searchTEActivity) {
        return searchTEActivity;
    }

    @Provides
    @PerActivity
    SearchTEContractsModule.Presenter providePresenter(D2 d2,
                                                       SearchRepository searchRepository,
                                                       SchedulerProvider schedulerProvider,
                                                       AnalyticsHelper analyticsHelper,
                                                       MapTeisToFeatureCollection mapTeisToFeatureCollection,
                                                       MapTeiEventsToFeatureCollection mapTeiEventsToFeatureCollection,
                                                       PreferenceProvider preferenceProvider) {
        return new SearchTEPresenter(view, d2, searchRepository, schedulerProvider,
                analyticsHelper, initialProgram, mapTeisToFeatureCollection, mapTeiEventsToFeatureCollection,
                new EventToEventUiComponent(), preferenceProvider);
    }

    @Provides
    @PerActivity
    MapTeisToFeatureCollection provideMapTeisToFeatureCollection() {
        return new MapTeisToFeatureCollection(new BoundsGeometry(),
                new MapPointToFeature(), new MapPolygonToFeature(), new MapPolygonPointToFeature(),
                new MapRelationshipToRelationshipMapModel(),
                new MapRelationshipsToFeatureCollection(
                        new MapLineRelationshipToFeature(),
                        new MapPointToFeature(),
                        new MapPolygonToFeature(),
                        new GetBoundingBox()
                ));
    }

    @Provides
    @PerActivity
    MapTeiEventsToFeatureCollection provideMapTeiEventsToFeatureCollection() {
        return new MapTeiEventsToFeatureCollection(
                new MapPointToFeature(),
                new MapPolygonToFeature(),
                new GetBoundingBox());
    }

    @Provides
    @PerActivity
    SearchRepository searchRepository(@NonNull D2 d2, FilterPresenter filterPresenter, ResourceManager resources, SearchSortingValueSetter searchSortingValueSetter) {
        return new SearchRepositoryImpl(teiType, d2, filterPresenter, resources, searchSortingValueSetter);
    }

    @Provides
    @PerActivity
    EnrollmentUiDataHelper enrollmentUiDataHelper(Context context) {
        return new EnrollmentUiDataHelper(context);
    }

    @Provides
    @PerActivity
    SearchSortingValueSetter searchSortingValueSetter(Context context, D2 d2, EnrollmentUiDataHelper enrollmentUiDataHelper) {
        String unknownLabel = context.getString(R.string.unknownValue);
        String eventDateLabel = context.getString(R.string.most_recent_event_date);
        String orgUnitLabel = context.getString(R.string.org_unit);
        String enrollmentStatusLabel = context.getString(R.string.filters_title_enrollment_status);
        String enrollmentDateDefaultLabel = context.getString(R.string.enrollment_date);
        String uiDateFormat = DateUtils.SIMPLE_DATE_FORMAT;
        return new SearchSortingValueSetter(d2,
                unknownLabel,
                eventDateLabel,
                orgUnitLabel,
                enrollmentStatusLabel,
                enrollmentDateDefaultLabel,
                uiDateFormat,
                enrollmentUiDataHelper);
    }

    @Provides
    @PerActivity
    CarouselViewAnimations animations() {
        return new CarouselViewAnimations();
    }

    @Provides
    @PerActivity
    FiltersAdapter provideFiltersAdapter(FilterPresenter filterPresenter) {
        return new FiltersAdapter(FiltersAdapter.ProgramType.TRACKER, filterPresenter);
    }
}
