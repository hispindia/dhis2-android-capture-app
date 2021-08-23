package org.medhis2yes.usescases.searchTrackEntity;

import android.content.Context;

import androidx.annotation.NonNull;

import org.medhis2yes.Bindings.ValueTypeExtensionsKt;
import org.medhis2yes.R;
import org.medhis2yes.animations.CarouselViewAnimations;
import org.medhis2yes.data.dagger.PerActivity;
import org.medhis2yes.data.dhislogic.DhisMapUtils;
import org.medhis2yes.data.dhislogic.DhisPeriodUtils;
import org.medhis2yes.data.enrollment.EnrollmentUiDataHelper;
import org.medhis2yes.data.filter.FilterPresenter;
import org.medhis2yes.data.filter.FilterRepository;
import org.medhis2yes.data.forms.dataentry.FormUiModelColorFactoryImpl;
import org.medhis2yes.data.forms.dataentry.fields.FieldViewModelFactory;
import org.medhis2yes.data.forms.dataentry.fields.FieldViewModelFactoryImpl;
import org.medhis2yes.data.prefs.PreferenceProvider;
import org.medhis2yes.data.schedulers.SchedulerProvider;
import org.medhis2yes.data.sorting.SearchSortingValueSetter;
import org.medhis2yes.form.data.FormRepository;
import org.medhis2yes.form.data.FormRepositoryNonPersistenceImpl;
import org.medhis2yes.form.ui.style.FormUiColorFactory;
import org.medhis2yes.uicomponents.map.geometry.bound.BoundsGeometry;
import org.medhis2yes.uicomponents.map.geometry.bound.GetBoundingBox;
import org.medhis2yes.uicomponents.map.geometry.line.MapLineRelationshipToFeature;
import org.medhis2yes.uicomponents.map.geometry.mapper.MapGeometryToFeature;
import org.medhis2yes.uicomponents.map.geometry.mapper.feature.MapCoordinateFieldToFeature;
import org.medhis2yes.uicomponents.map.geometry.mapper.featurecollection.MapAttributeToFeature;
import org.medhis2yes.uicomponents.map.geometry.mapper.featurecollection.MapCoordinateFieldToFeatureCollection;
import org.medhis2yes.uicomponents.map.geometry.mapper.featurecollection.MapDataElementToFeature;
import org.medhis2yes.uicomponents.map.geometry.mapper.featurecollection.MapRelationshipsToFeatureCollection;
import org.medhis2yes.uicomponents.map.geometry.mapper.featurecollection.MapTeiEventsToFeatureCollection;
import org.medhis2yes.uicomponents.map.geometry.mapper.featurecollection.MapTeisToFeatureCollection;
import org.medhis2yes.uicomponents.map.geometry.point.MapPointToFeature;
import org.medhis2yes.uicomponents.map.geometry.polygon.MapPolygonPointToFeature;
import org.medhis2yes.uicomponents.map.geometry.polygon.MapPolygonToFeature;
import org.medhis2yes.uicomponents.map.mapper.EventToEventUiComponent;
import org.medhis2yes.uicomponents.map.mapper.MapRelationshipToRelationshipMapModel;
import org.medhis2yes.utils.DateUtils;
import org.medhis2yes.utils.analytics.AnalyticsHelper;
import org.medhis2yes.utils.analytics.matomo.MatomoAnalyticsController;
import org.medhis2yes.utils.filters.DisableHomeFiltersFromSettingsApp;
import org.medhis2yes.utils.filters.FiltersAdapter;
import org.medhis2yes.utils.filters.workingLists.TeiFilterToWorkingListItemMapper;
import org.medhis2yes.utils.resources.ResourceManager;
import org.hisp.dhis.android.core.D2;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public class SearchTEModule {

    private final SearchTEContractsModule.View view;
    private final String teiType;
    private final String initialProgram;
    private final Context moduleContext;

    public SearchTEModule(SearchTEContractsModule.View view,
                          String tEType,
                          String initialProgram,
                          Context context) {
        this.view = view;
        this.teiType = tEType;
        this.initialProgram = initialProgram;
        this.moduleContext = context;
    }

    @Provides
    @PerActivity
    SearchTEContractsModule.View provideView(SearchTEActivity searchTEActivity) {
        return searchTEActivity;
    }

    @Provides
    @PerActivity
    SearchTEContractsModule.Presenter providePresenter(D2 d2,
                                                       DhisMapUtils mapUtils,
                                                       SearchRepository searchRepository,
                                                       SchedulerProvider schedulerProvider,
                                                       AnalyticsHelper analyticsHelper,
                                                       MapTeisToFeatureCollection mapTeisToFeatureCollection,
                                                       MapTeiEventsToFeatureCollection mapTeiEventsToFeatureCollection,
                                                       MapCoordinateFieldToFeatureCollection mapCoordinateFieldToFeatureCollection,
                                                       PreferenceProvider preferenceProvider,
                                                       TeiFilterToWorkingListItemMapper teiWorkingListMapper,
                                                       FilterRepository filterRepository,
                                                       FieldViewModelFactory fieldViewModelFactory,
                                                       MatomoAnalyticsController matomoAnalyticsController,
                                                       FormRepository formRepository) {
        return new SearchTEPresenter(view, d2, mapUtils, searchRepository, schedulerProvider,
                analyticsHelper, initialProgram, mapTeisToFeatureCollection, mapTeiEventsToFeatureCollection, mapCoordinateFieldToFeatureCollection,
                new EventToEventUiComponent(), preferenceProvider,
                teiWorkingListMapper, filterRepository, fieldViewModelFactory.fieldProcessor(),
                new DisableHomeFiltersFromSettingsApp(), matomoAnalyticsController, formRepository);
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
    SearchRepository searchRepository(@NonNull D2 d2, FilterPresenter filterPresenter, ResourceManager resources, SearchSortingValueSetter searchSortingValueSetter, FieldViewModelFactory fieldFactory, DhisPeriodUtils periodUtils) {
        return new SearchRepositoryImpl(teiType, d2, filterPresenter, resources, searchSortingValueSetter, fieldFactory, periodUtils);
    }

    @Provides
    @PerActivity
    FieldViewModelFactory fieldViewModelFactory(Context context, FormUiColorFactory colorFactory) {
        return new FieldViewModelFactoryImpl(ValueTypeExtensionsKt.valueTypeHintMap(context), true, colorFactory);
    }

    @Provides
    @PerActivity
    FormUiColorFactory provideFormUiColorFactory() {
        return new FormUiModelColorFactoryImpl(moduleContext, false);
    }

    @Provides
    @PerActivity
    MapCoordinateFieldToFeatureCollection provideMapDataElementToFeatureCollection(MapAttributeToFeature attributeToFeatureMapper, MapDataElementToFeature dataElementToFeatureMapper) {
        return new MapCoordinateFieldToFeatureCollection(dataElementToFeatureMapper, attributeToFeatureMapper);
    }

    @Provides
    @PerActivity
    MapGeometryToFeature provideMapGeometryToFeature() {
        return new MapGeometryToFeature(new MapPointToFeature(), new MapPolygonToFeature());
    }

    @Provides
    @PerActivity
    MapCoordinateFieldToFeature provideMapCoordinateFieldToFeature(MapGeometryToFeature mapGeometryToFeature) {
        return new MapCoordinateFieldToFeature(mapGeometryToFeature);
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
        String enrollmentStatusLabel = context.getString(R.string.filters_title_enrollment_status);
        String enrollmentDateDefaultLabel = context.getString(R.string.enrollment_date);
        String uiDateFormat = DateUtils.SIMPLE_DATE_FORMAT;
        return new SearchSortingValueSetter(d2,
                unknownLabel,
                eventDateLabel,
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
    FiltersAdapter provideNewFiltersAdapter() {
        return new FiltersAdapter();
    }

    @Provides
    @PerActivity
    FormRepository provideFormRepository() {
        return new FormRepositoryNonPersistenceImpl();
    }
}
