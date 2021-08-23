package org.medhis2yes.usescases.programEventDetail;

import androidx.annotation.NonNull;

import org.medhis2yes.animations.CarouselViewAnimations;
import org.medhis2yes.data.dagger.PerActivity;
import org.medhis2yes.data.dhislogic.DhisMapUtils;
import org.medhis2yes.data.filter.FilterPresenter;
import org.medhis2yes.data.filter.FilterRepository;
import org.medhis2yes.data.schedulers.SchedulerProvider;
import org.medhis2yes.uicomponents.map.geometry.bound.GetBoundingBox;
import org.medhis2yes.uicomponents.map.geometry.mapper.MapGeometryToFeature;
import org.medhis2yes.uicomponents.map.geometry.mapper.feature.MapCoordinateFieldToFeature;
import org.medhis2yes.uicomponents.map.geometry.mapper.featurecollection.MapAttributeToFeature;
import org.medhis2yes.uicomponents.map.geometry.mapper.featurecollection.MapCoordinateFieldToFeatureCollection;
import org.medhis2yes.uicomponents.map.geometry.mapper.featurecollection.MapDataElementToFeature;
import org.medhis2yes.uicomponents.map.geometry.mapper.featurecollection.MapEventToFeatureCollection;
import org.medhis2yes.uicomponents.map.geometry.point.MapPointToFeature;
import org.medhis2yes.uicomponents.map.geometry.polygon.MapPolygonToFeature;
import org.medhis2yes.utils.analytics.matomo.MatomoAnalyticsController;
import org.medhis2yes.utils.filters.DisableHomeFiltersFromSettingsApp;
import org.medhis2yes.utils.filters.FilterManager;
import org.medhis2yes.utils.filters.FiltersAdapter;
import org.medhis2yes.utils.filters.workingLists.EventFilterToWorkingListItemMapper;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class ProgramEventDetailModule {


    private final String programUid;
    private ProgramEventDetailContract.View view;

    public ProgramEventDetailModule(ProgramEventDetailContract.View view, String programUid) {
        this.view = view;
        this.programUid = programUid;
    }

    @Provides
    @PerActivity
    ProgramEventDetailContract.View provideView(ProgramEventDetailActivity activity) {
        return activity;
    }

    @Provides
    @PerActivity
    ProgramEventDetailContract.Presenter providesPresenter(
            @NonNull ProgramEventDetailRepository programEventDetailRepository, SchedulerProvider schedulerProvider, FilterManager filterManager,
            EventFilterToWorkingListItemMapper eventWorkingListMapper,
            FilterRepository filterRepository,
            FilterPresenter filterPresenter, MatomoAnalyticsController matomoAnalyticsController) {
        return new ProgramEventDetailPresenter(view, programEventDetailRepository, schedulerProvider, filterManager,
                eventWorkingListMapper,
                filterRepository,
                filterPresenter, new DisableHomeFiltersFromSettingsApp(),
                matomoAnalyticsController);
    }

    @Provides
    @PerActivity
    MapGeometryToFeature provideMapGeometryToFeature() {
        return new MapGeometryToFeature(new MapPointToFeature(), new MapPolygonToFeature());
    }

    @Provides
    @PerActivity
    MapEventToFeatureCollection provideMapEventToFeatureCollection(MapGeometryToFeature mapGeometryToFeature) {
        return new MapEventToFeatureCollection(mapGeometryToFeature,
                new GetBoundingBox());
    }

    @Provides
    @PerActivity
    MapCoordinateFieldToFeatureCollection provideMapDataElementToFeatureCollection(MapAttributeToFeature attributeToFeatureMapper, MapDataElementToFeature dataElementToFeatureMapper) {
        return new MapCoordinateFieldToFeatureCollection(dataElementToFeatureMapper, attributeToFeatureMapper);
    }

    @Provides
    @PerActivity
    MapCoordinateFieldToFeature provideMapCoordinateFieldToFeature(MapGeometryToFeature mapGeometryToFeature) {
        return new MapCoordinateFieldToFeature(mapGeometryToFeature);
    }

    @Provides
    @PerActivity
    ProgramEventDetailRepository eventDetailRepository(D2 d2,
                                                       ProgramEventMapper mapper,
                                                       MapEventToFeatureCollection mapEventToFeatureCollection,
                                                       MapCoordinateFieldToFeatureCollection mapCoordinateFieldToFeatureCollection,
                                                       DhisMapUtils dhisMapUtils,
                                                       FilterPresenter filterPresenter) {
        return new ProgramEventDetailRepositoryImpl(programUid, d2, mapper, mapEventToFeatureCollection, mapCoordinateFieldToFeatureCollection, dhisMapUtils, filterPresenter);
    }

    @Provides
    @PerActivity
    CarouselViewAnimations animations() {
        return new CarouselViewAnimations();
    }

    @Provides
    @PerActivity
    FiltersAdapter provideNewFiltersAdapter() {
        return new FiltersAdapter();
    }
}
