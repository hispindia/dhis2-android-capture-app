package org.dhis2.data.forms.dataentry;


import android.support.annotation.NonNull;
import android.support.annotation.UiThread;

import org.dhis2.data.forms.dataentry.fields.FieldViewModel;
import org.dhis2.data.metadata.MetadataRepository;
import org.hisp.dhis.android.core.organisationunit.OrganisationUnitModel;
import org.hisp.dhis.android.core.trackedentity.TrackedEntityInstanceModel;

import java.util.List;

import io.reactivex.Observable;

interface DataEntryPresenter {
    @UiThread
    void onAttach(@NonNull DataEntryView view);

    @UiThread
    void onDetach();

    @NonNull
    Observable<List<OrganisationUnitModel>> getOrgUnits();

    @NonNull
    Observable<List<TrackedEntityInstanceModel>> getTeis();

    @NonNull
    MetadataRepository getMetadataRepository();

}
