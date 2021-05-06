package org.fpandhis2.usescases.teiDashboard.dashboardfragments.relationships;

import org.fpandhis2.animations.CarouselViewAnimations;
import org.fpandhis2.data.dagger.PerFragment;
import org.fpandhis2.data.schedulers.SchedulerProvider;
import org.fpandhis2.uicomponents.map.geometry.bound.GetBoundingBox;
import org.fpandhis2.uicomponents.map.geometry.line.MapLineRelationshipToFeature;
import org.fpandhis2.uicomponents.map.geometry.mapper.featurecollection.MapRelationshipsToFeatureCollection;
import org.fpandhis2.uicomponents.map.geometry.point.MapPointToFeature;
import org.fpandhis2.uicomponents.map.geometry.polygon.MapPolygonToFeature;
import org.fpandhis2.uicomponents.map.mapper.MapRelationshipToRelationshipMapModel;
import org.fpandhis2.usescases.teiDashboard.DashboardRepository;
import org.fpandhis2.utils.analytics.AnalyticsHelper;
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
