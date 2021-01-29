package org.dhis2hiv.usescases.teiDashboard.dashboardfragments.relationships;

import org.dhis2hiv.animations.CarouselViewAnimations;
import org.dhis2hiv.data.dagger.PerFragment;
import org.dhis2hiv.data.schedulers.SchedulerProvider;
import org.dhis2hiv.uicomponents.map.geometry.bound.GetBoundingBox;
import org.dhis2hiv.uicomponents.map.geometry.line.MapLineRelationshipToFeature;
import org.dhis2hiv.uicomponents.map.geometry.mapper.featurecollection.MapRelationshipsToFeatureCollection;
import org.dhis2hiv.uicomponents.map.geometry.point.MapPointToFeature;
import org.dhis2hiv.uicomponents.map.geometry.polygon.MapPolygonToFeature;
import org.dhis2hiv.uicomponents.map.mapper.MapRelationshipToRelationshipMapModel;
import org.dhis2hiv.usescases.teiDashboard.DashboardRepository;
import org.dhis2hiv.utils.analytics.AnalyticsHelper;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

/**
 * QUADRAM. Created by ppajuelo on 09/04/2019.
 */
@PerFragment
@Module
public class RelationshipModule {

    private final String programUid;
    private final String teiUid;
    private final RelationshipView view;

    public RelationshipModule(RelationshipView view, String programUid, String teiUid) {
        this.programUid = programUid;
        this.teiUid = teiUid;
        this.view = view;
    }

    @Provides
    @PerFragment
    RelationshipPresenter providesPresenter(D2 d2,
                                            DashboardRepository dashboardRepository,
                                            SchedulerProvider schedulerProvider,
                                            AnalyticsHelper analyticsHelper,
                                            MapRelationshipsToFeatureCollection mapRelationshipsToFeatureCollection) {
        return new RelationshipPresenter(view, d2, programUid, teiUid, dashboardRepository, schedulerProvider, analyticsHelper ,new MapRelationshipToRelationshipMapModel(), mapRelationshipsToFeatureCollection );
    }

    @Provides
    @PerFragment
    MapRelationshipsToFeatureCollection provideMapRelationshipToFeatureCollection(){
        return new MapRelationshipsToFeatureCollection(
                new MapLineRelationshipToFeature(),
                new MapPointToFeature(),
                new MapPolygonToFeature(),
                new GetBoundingBox()
        );
    }

    @Provides
    @PerFragment
    CarouselViewAnimations animations(){
        return new CarouselViewAnimations();
    }
}
