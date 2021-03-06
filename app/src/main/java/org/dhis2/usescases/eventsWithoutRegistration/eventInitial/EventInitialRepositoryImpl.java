package org.dhis2.usescases.eventsWithoutRegistration.eventInitial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.dhis2.data.forms.FormSectionViewModel;
import org.dhis2.utils.DateUtils;
import org.hisp.dhis.android.core.D2;
import org.hisp.dhis.android.core.arch.helpers.UidsHelper;
import org.hisp.dhis.android.core.arch.repositories.scope.RepositoryScope;
import org.hisp.dhis.android.core.category.Category;
import org.hisp.dhis.android.core.category.CategoryCombo;
import org.hisp.dhis.android.core.category.CategoryOption;
import org.hisp.dhis.android.core.category.CategoryOptionCombo;
import org.hisp.dhis.android.core.common.FeatureType;
import org.hisp.dhis.android.core.common.Geometry;
import org.hisp.dhis.android.core.common.ObjectStyle;
import org.hisp.dhis.android.core.enrollment.EnrollmentStatus;
import org.hisp.dhis.android.core.event.Event;
import org.hisp.dhis.android.core.event.EventCreateProjection;
import org.hisp.dhis.android.core.event.EventObjectRepository;
import org.hisp.dhis.android.core.event.EventStatus;
import org.hisp.dhis.android.core.maintenance.D2Error;
import org.hisp.dhis.android.core.organisationunit.OrganisationUnit;
import org.hisp.dhis.android.core.program.Program;
import org.hisp.dhis.android.core.program.ProgramStage;
import org.hisp.dhis.android.core.program.ProgramStageSection;
import org.hisp.dhis.android.core.program.ProgramStageSectionRenderingType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import timber.log.Timber;

public class EventInitialRepositoryImpl implements EventInitialRepository {

    private final String eventUid;
    private final D2 d2;
    private final String stageUid;

    EventInitialRepositoryImpl(String eventUid, String stageUid, D2 d2) {
        this.eventUid = eventUid;
        this.stageUid = stageUid;
        this.d2 = d2;
    }

    @NonNull
    @Override
    public Observable<Event> event(String eventId) {
        return d2.eventModule().events().uid(eventId).get().toObservable();
    }

    @NonNull
    @Override
    public Observable<List<OrganisationUnit>> filteredOrgUnits(String date, String programId, String parentId) {
        return (parentId == null ? orgUnits(programId) : orgUnits(programId, parentId))
                .map(organisationUnits -> {
                    if (date == null) {
                        return organisationUnits;
                    }
                    Iterator<OrganisationUnit> iterator = organisationUnits.iterator();
                    while (iterator.hasNext()) {
                        OrganisationUnit organisationUnit = iterator.next();
                        if (organisationUnit.openingDate() != null && organisationUnit.openingDate().after(DateUtils.databaseDateFormat().parse(date))
                                || organisationUnit.closedDate() != null && organisationUnit.closedDate().before(DateUtils.databaseDateFormat().parse(date)))
                            iterator.remove();
                    }
                    return organisationUnits;
                });
    }

    @NonNull
    @Override
    public Observable<List<OrganisationUnit>> orgUnits(String programId) {
        return d2.organisationUnitModule().organisationUnits()
                .byOrganisationUnitScope(OrganisationUnit.Scope.SCOPE_DATA_CAPTURE)
                .byProgramUids(Collections.singletonList(programId))
                .get()
                .toObservable();
    }

    public Observable<List<OrganisationUnit>> orgUnits(String programId, String parentUid) {
        return d2.organisationUnitModule().organisationUnits()
                .byProgramUids(Collections.singletonList(programId))
                .byParentUid().eq(parentUid)
                .byOrganisationUnitScope(OrganisationUnit.Scope.SCOPE_DATA_CAPTURE)
                .get()
                .toObservable();
    }

    @NonNull
    @Override
    public Observable<CategoryCombo> catCombo(String programUid) {
        return d2.programModule().programs().uid(programUid).get()
                .flatMap(program -> d2.categoryModule().categoryCombos()
                        .withCategories()
                        .withCategoryOptionCombos()
                        .uid(program.categoryComboUid()
                        ).get()).toObservable();
    }

    @Override
    public Observable<List<CategoryOptionCombo>> catOptionCombos(String catOptionComboUid) {
        return d2.categoryModule().categoryOptionCombos().byCategoryComboUid().eq(catOptionComboUid).get().toObservable();
    }

    @Override
    public Flowable<Map<String, CategoryOption>> getOptionsFromCatOptionCombo(String eventId) {
        return d2.eventModule().events().uid(eventUid).get().toFlowable()
                .flatMap(event -> catCombo(event.program()).toFlowable(BackpressureStrategy.LATEST)
                        .flatMap(categoryCombo -> {
                            Map<String, CategoryOption> map = new HashMap<>();
                            if (!categoryCombo.isDefault() && event.attributeOptionCombo() != null) {
                                List<CategoryOption> selectedCatOptions = d2.categoryModule().categoryOptionCombos().withCategoryOptions().uid(event.attributeOptionCombo()).blockingGet().categoryOptions();
                                for (Category category : categoryCombo.categories()) {
                                    for (CategoryOption categoryOption : selectedCatOptions) {
                                        List<CategoryOption> categoryOptions = d2.categoryModule().categoryOptions().byCategoryUid(category.uid()).blockingGet();
                                        if (categoryOptions.contains(categoryOption))
                                            map.put(category.uid(), categoryOption);
                                    }
                                }
                            }

                            return Flowable.just(map);
                        }));
    }

    @Override
    public Date getStageLastDate(String programStageUid, String enrollmentUid) {
        List<Event> activeEvents = d2.eventModule().events().byEnrollmentUid().eq(enrollmentUid).byProgramStageUid().eq(programStageUid)
                .orderByEventDate(RepositoryScope.OrderByDirection.DESC).blockingGet();
        List<Event> scheduleEvents = d2.eventModule().events().byEnrollmentUid().eq(enrollmentUid).byProgramStageUid().eq(programStageUid)
                .orderByDueDate(RepositoryScope.OrderByDirection.DESC).blockingGet();

        Date activeDate = null;
        Date scheduleDate = null;
        if (!activeEvents.isEmpty()) {
            activeDate = activeEvents.get(0).eventDate();
        }
        if (!scheduleEvents.isEmpty())
            scheduleDate = scheduleEvents.get(0).dueDate();

        if (activeDate != null && scheduleDate != null) {
            return activeDate.before(scheduleDate) ? scheduleDate : activeDate;
        } else if (activeDate != null) {
            return activeDate;
        } else if (scheduleDate != null) {
            return scheduleDate;
        } else {
            return Calendar.getInstance().getTime();
        }
    }

    @Override
    public Observable<String> createEvent(String enrollmentUid, @Nullable String trackedEntityInstanceUid,
                                          @NonNull String programUid,
                                          @NonNull String programStage, @NonNull Date date,
                                          @NonNull String orgUnitUid, @Nullable String categoryOptionsUid,
                                          @Nullable String categoryOptionComboUid, @NonNull Geometry geometry) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return Observable.fromCallable(() ->
                d2.eventModule().events().blockingAdd(
                        EventCreateProjection.builder()
                                .enrollment(enrollmentUid)
                                .program(programUid)
                                .programStage(programStage)
                                .organisationUnit(orgUnitUid)
                                .attributeOptionCombo(categoryOptionComboUid)
                                .build()
                )
        ).map(uid -> {
            EventObjectRepository eventRepository = d2.eventModule().events().uid(uid);
            eventRepository.setEventDate(cal.getTime());
            if (d2.programModule().programStages().uid(eventRepository.blockingGet().programStage()).blockingGet().featureType() != null)
                switch (d2.programModule().programStages().uid(eventRepository.blockingGet().programStage()).blockingGet().featureType()) {
                    case POINT:
                    case POLYGON:
                    case MULTI_POLYGON:
                        eventRepository.setGeometry(geometry);
                        break;
                    default:
                        break;
                }
            return uid;
        });
    }

    @Override
    public Observable<String> scheduleEvent(String enrollmentUid, @Nullable String trackedEntityInstanceUid,
                                            @NonNull String programUid, @NonNull String programStage,
                                            @NonNull Date dueDate, @NonNull String orgUnitUid, @Nullable String categoryOptionsUid,
                                            @Nullable String categoryOptionComboUid, @NonNull Geometry geometry) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dueDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return Observable.fromCallable(() ->
                d2.eventModule().events().blockingAdd(
                        EventCreateProjection.builder()
                                .enrollment(enrollmentUid)
                                .program(programUid)
                                .programStage(programStage)
                                .organisationUnit(orgUnitUid)
                                .attributeOptionCombo(categoryOptionComboUid)
                                .build()
                )
        ).map(uid -> {
            EventObjectRepository eventRepository = d2.eventModule().events().uid(uid);
            eventRepository.setDueDate(cal.getTime());
            eventRepository.setStatus(EventStatus.SCHEDULE);
            if (d2.programModule().programStages().uid(eventRepository.blockingGet().programStage()).blockingGet().featureType() != null)
                switch (d2.programModule().programStages().uid(eventRepository.blockingGet().programStage()).blockingGet().featureType()) {
                    case POINT:
                    case POLYGON:
                    case MULTI_POLYGON:
                        eventRepository.setGeometry(geometry);
                        break;
                    default:
                        break;
                }
            return uid;
        });
    }

    @NonNull
    @Override
    public Observable<ProgramStage> programStage(String programUid) {
        return d2.programModule().programStages().byProgramUid().eq(programUid).one().get().toObservable();
    }

    @NonNull
    @Override
    public Observable<ProgramStage> programStageWithId(String programStageUid) {
        return d2.programModule().programStages().byUid().eq(programStageUid).one().get().toObservable();
    }


    @NonNull
    @Override
    public Observable<Event> editEvent(String trackedEntityInstance,
                                       String eventUid,
                                       String date,
                                       String orgUnitUid,
                                       String catComboUid,
                                       String catOptionCombo,
                                       Geometry geometry) {

        return Observable.fromCallable(() -> d2.eventModule().events().uid(eventUid))
                .map(eventRepository -> {
                    eventRepository.setEventDate(DateUtils.databaseDateFormat().parse(date));
                    eventRepository.setOrganisationUnitUid(orgUnitUid);
                    eventRepository.setAttributeOptionComboUid(catOptionCombo);
                    FeatureType featureType = d2.programModule().programStages().uid(eventRepository.blockingGet().programStage()).blockingGet().featureType();
                    if (featureType != null)
                        switch (featureType) {
                            case NONE:
                                break;
                            case POINT:
                            case POLYGON:
                            case MULTI_POLYGON:
                                eventRepository.setGeometry(geometry);
                                break;
                            default:
                                break;
                        }
                    return eventRepository.blockingGet();
                });
    }

    @Override
    public Observable<Boolean> accessDataWrite(String programUid) {
        if (eventUid != null)
            return d2.eventModule().events().uid(eventUid).get().toObservable()
                    .flatMap(event -> {
                        if (event.attributeOptionCombo() != null)
                            return accessWithCatOption(programUid, event.attributeOptionCombo());
                        else
                            return programAccess(programUid);
                    });
        else
            return programAccess(programUid);


    }

    private Observable<Boolean> accessWithCatOption(String programUid, String catOptionCombo) {
        return d2.categoryModule().categoryOptionCombos().withCategoryOptions().uid(catOptionCombo).get()
                .map(data -> UidsHelper.getUidsList(data.categoryOptions()))
                .flatMap(categoryOptionsUids -> d2.categoryModule().categoryOptions().byUid().in(categoryOptionsUids).get())
                .toObservable()
                .map(categoryOptions -> {
                    boolean access = true;
                    for (CategoryOption option : categoryOptions) {
                        if (!option.access().data().write())
                            access = false;
                    }
                    return access;
                }).flatMap(catComboAccess -> {
                    if (catComboAccess)
                        return programAccess(programUid);
                    else
                        return Observable.just(catComboAccess);
                });
    }

    private Observable<Boolean> programAccess(String programUid) {
        return Observable.fromCallable(() -> {
                    boolean programAccess = d2.programModule().programs().uid(programUid).blockingGet().access().data().write();
                    boolean stageAccess = true;
                    if (stageUid != null) {
                        stageAccess = d2.programModule().programStages().uid(stageUid).blockingGet().access().data().write();
                    }
                    return programAccess && stageAccess;
                }
        );
    }

    @Override
    public void deleteEvent(String eventId, String trackedEntityInstance) {
        try {
            d2.eventModule().events().uid(eventId).blockingDelete();
        } catch (D2Error d2Error) {
            Timber.e(d2Error);
        }
    }

    @Override
    public boolean isEnrollmentOpen() {
        Event event = d2.eventModule().events().uid(eventUid).blockingGet();
        return event == null || event.enrollment() == null || d2.enrollmentModule().enrollments().uid(event.enrollment()).blockingGet().status() == EnrollmentStatus.ACTIVE;
    }


    @Override
    public Observable<Program> getProgramWithId(String programUid) {
        return d2.programModule().programs()
                .withTrackedEntityType().byUid().eq(programUid).one().get().toObservable();
    }

    @Override
    public Flowable<ProgramStage> programStageForEvent(String eventId) {
        return d2.eventModule().events().byUid().eq(eventId).one().get().toFlowable()
                .map(event -> d2.programModule().programStages().byUid().eq(event.programStage()).one().blockingGet());
    }

    @Override
    public Observable<OrganisationUnit> getOrganisationUnit(String orgUnitUid) {
        return d2.organisationUnitModule().organisationUnits().byUid().eq(orgUnitUid).one().get().toObservable();
    }

    @Override
    public Observable<ObjectStyle> getObjectStyle(String uid) {
        return d2.programModule().programStages().uid(uid).get()
                .map(programStage -> {
                    Program program = d2.programModule().programs().uid(programStage.program().uid()).blockingGet();
                    ObjectStyle programStyle = program.style() != null ? program.style() : ObjectStyle.builder().build();
                    if (programStage.style() != null) {
                        programStage.style().icon();
                        programStage.style().color();
                        return ObjectStyle.builder()
                                .icon(programStage.style().icon() != null ? programStage.style().icon() : programStyle.icon())
                                .color(programStage.style().color() != null ? programStage.style().color() : programStyle.color())
                                .build();
                    } else {
                        return programStyle;
                    }
                }).toObservable();
    }

    @Override
    public String getCategoryOptionCombo(String categoryComboUid, List<String> categoryOptionsUid) {
        return d2.categoryModule().categoryOptionCombos()
                .byCategoryComboUid().eq(categoryComboUid)
                .byCategoryOptions(categoryOptionsUid)
                .one().blockingGet().uid();
    }

    @Override
    public CategoryOption getCatOption(String selectedOption) {
        return d2.categoryModule().categoryOptions().uid(selectedOption).blockingGet();
    }

    @Override
    public int getCatOptionSize(String uid) {
        return d2.categoryModule().categoryOptions()
                .byCategoryUid(uid)
                .byAccessDataWrite().isTrue()
                .blockingCount();
    }

    @Override
    public List<CategoryOption> getCategoryOptions(String categoryUid) {
        return d2.categoryModule().categoryOptions()
                .byCategoryUid(categoryUid)
                .blockingGet();
    }

    @Override
    public boolean showCompletionPercentage() {
        if (d2.settingModule().appearanceSettings().blockingExists()) {
            return d2.settingModule().appearanceSettings().getCompletionSpinnerByUid(
                    d2.eventModule().events().uid(eventUid).blockingGet().program()
            ).visible();
        }
        return true;
    }

    @Override
    public Flowable<List<FormSectionViewModel>> eventSections() {
        return d2.eventModule().events().uid(eventUid).get()
                .map(eventSingle -> {
                    List<FormSectionViewModel> formSection = new ArrayList<>();
                    if (eventSingle.deleted() == null || !eventSingle.deleted()) {
                        ProgramStage stage = d2.programModule().programStages().uid(eventSingle.programStage()).blockingGet();
                        List<ProgramStageSection> stageSections = d2.programModule().programStageSections().byProgramStageUid().eq(stage.uid()).blockingGet();
                        if (stageSections.size() > 0) {
                            Collections.sort(stageSections, (one, two) ->
                                    one.sortOrder().compareTo(two.sortOrder()));

                            for (ProgramStageSection section : stageSections)
                                formSection.add(FormSectionViewModel.createForSection(
                                        eventUid,
                                        section.uid(),
                                        section.displayName(),
                                        section.renderType().mobile() != null ?
                                                section.renderType().mobile().type().name() :
                                                null)
                                );
                        } else {
                            formSection.add(FormSectionViewModel.createForSection(
                                    eventUid,
                                    "",
                                    "",
                                    ProgramStageSectionRenderingType.LISTING.name()));
                        }
                    }
                    return formSection;
                }).toFlowable();
    }
}