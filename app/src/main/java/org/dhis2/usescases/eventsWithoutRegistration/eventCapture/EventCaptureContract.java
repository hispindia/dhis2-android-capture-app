package org.dhis2.usescases.eventsWithoutRegistration.eventCapture;

import org.dhis2.data.forms.FormSectionViewModel;
import org.dhis2.data.forms.dataentry.fields.TrackerAssociate.TrackedEntityInstanceViewModel;
import org.dhis2.data.metadata.MetadataRepository;
import org.dhis2.usescases.general.AbstractActivityContracts;
import org.hisp.dhis.android.core.organisationunit.OrganisationUnitModel;
import org.hisp.dhis.android.core.trackedentity.TrackedEntityInstanceModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * QUADRAM. Created by ppajuelo on 19/11/2018.
 */
public class EventCaptureContract {

    public interface View extends AbstractActivityContracts.View {

        void renderInitialInfo(String stageName, String eventDate, String orgUnit, String catOption);

        EventCaptureContract.Presenter getPresenter();
    }

    public interface Presenter extends AbstractActivityContracts.Presenter {
        void init(EventCaptureContract.View view);

        void subscribeToSection();

        void onNextSection();

        void onPreviousSection();

        Observable<List<OrganisationUnitModel>> getOrgUnits();

        Observable<List<TrackedEntityInstanceModel>> getTeis();

        MetadataRepository getMetadataRepository();
    }

    public interface EventCaptureRepository {

        Flowable<String> programStageName();

        Flowable<String> eventDate();

        Flowable<String> orgUnit();

        Flowable<String> catOption();

        Flowable<List<FormSectionViewModel>> eventSections();
    }

}
