package org.dhis2hiv.data.user;

import androidx.annotation.NonNull;

import org.dhis2hiv.data.dagger.PerUser;
import org.dhis2hiv.data.service.ReservedValuesWorkerComponent;
import org.dhis2hiv.data.service.ReservedValuesWorkerModule;
import org.dhis2hiv.data.service.SyncDataWorkerComponent;
import org.dhis2hiv.data.service.SyncDataWorkerModule;
import org.dhis2hiv.data.service.SyncGranularRxComponent;
import org.dhis2hiv.data.service.SyncGranularRxModule;
import org.dhis2hiv.data.service.SyncInitWorkerComponent;
import org.dhis2hiv.data.service.SyncInitWorkerModule;
import org.dhis2hiv.data.service.SyncMetadataWorkerComponent;
import org.dhis2hiv.data.service.SyncMetadataWorkerModule;
import org.dhis2hiv.usescases.about.AboutComponent;
import org.dhis2hiv.usescases.about.AboutModule;
import org.dhis2hiv.usescases.datasets.dataSetTable.DataSetTableComponent;
import org.dhis2hiv.usescases.datasets.dataSetTable.DataSetTableModule;
import org.dhis2hiv.usescases.datasets.dataSetTable.dataSetSection.DataValueComponent;
import org.dhis2hiv.usescases.datasets.dataSetTable.dataSetSection.DataValueModule;
import org.dhis2hiv.usescases.datasets.datasetDetail.DataSetDetailComponent;
import org.dhis2hiv.usescases.datasets.datasetDetail.DataSetDetailModule;
import org.dhis2hiv.usescases.datasets.datasetInitial.DataSetInitialComponent;
import org.dhis2hiv.usescases.datasets.datasetInitial.DataSetInitialModule;
import org.dhis2hiv.usescases.enrollment.EnrollmentComponent;
import org.dhis2hiv.usescases.enrollment.EnrollmentModule;
import org.dhis2hiv.usescases.events.ScheduledEventComponent;
import org.dhis2hiv.usescases.events.ScheduledEventModule;
import org.dhis2hiv.usescases.eventsWithoutRegistration.eventCapture.EventCaptureComponent;
import org.dhis2hiv.usescases.eventsWithoutRegistration.eventCapture.EventCaptureModule;
import org.dhis2hiv.usescases.eventsWithoutRegistration.eventInitial.EventInitialComponent;
import org.dhis2hiv.usescases.eventsWithoutRegistration.eventInitial.EventInitialModule;
import org.dhis2hiv.usescases.eventsWithoutRegistration.eventSummary.EventSummaryComponent;
import org.dhis2hiv.usescases.eventsWithoutRegistration.eventSummary.EventSummaryModule;
import org.dhis2hiv.usescases.main.MainComponent;
import org.dhis2hiv.usescases.main.MainModule;
import org.dhis2hiv.usescases.main.program.ProgramComponent;
import org.dhis2hiv.usescases.main.program.ProgramModule;
import org.dhis2hiv.usescases.notes.NotesComponent;
import org.dhis2hiv.usescases.notes.NotesModule;
import org.dhis2hiv.usescases.notes.noteDetail.NoteDetailComponent;
import org.dhis2hiv.usescases.notes.noteDetail.NoteDetailModule;
import org.dhis2hiv.usescases.orgunitselector.OUTreeComponent;
import org.dhis2hiv.usescases.orgunitselector.OUTreeModule;
import org.dhis2hiv.usescases.programEventDetail.ProgramEventDetailComponent;
import org.dhis2hiv.usescases.programEventDetail.ProgramEventDetailModule;
import org.dhis2hiv.usescases.programStageSelection.ProgramStageSelectionComponent;
import org.dhis2hiv.usescases.programStageSelection.ProgramStageSelectionModule;
import org.dhis2hiv.usescases.qrCodes.QrComponent;
import org.dhis2hiv.usescases.qrCodes.QrModule;
import org.dhis2hiv.usescases.qrCodes.eventsworegistration.QrEventsWORegistrationComponent;
import org.dhis2hiv.usescases.qrCodes.eventsworegistration.QrEventsWORegistrationModule;
import org.dhis2hiv.usescases.qrReader.QrReaderComponent;
import org.dhis2hiv.usescases.qrReader.QrReaderModule;
import org.dhis2hiv.usescases.qrScanner.ScanComponent;
import org.dhis2hiv.usescases.qrScanner.ScanModule;
import org.dhis2hiv.usescases.reservedValue.ReservedValueComponent;
import org.dhis2hiv.usescases.reservedValue.ReservedValueModule;
import org.dhis2hiv.usescases.searchTrackEntity.SearchTEComponent;
import org.dhis2hiv.usescases.searchTrackEntity.SearchTEModule;
import org.dhis2hiv.usescases.settings.SyncManagerComponent;
import org.dhis2hiv.usescases.settings.SyncManagerModule;
import org.dhis2hiv.usescases.settingsprogram.ProgramSettingsComponent;
import org.dhis2hiv.usescases.settingsprogram.SettingsProgramModule;
import org.dhis2hiv.usescases.sms.SmsComponent;
import org.dhis2hiv.usescases.sms.SmsModule;
import org.dhis2hiv.usescases.sync.SyncComponent;
import org.dhis2hiv.usescases.sync.SyncModule;
import org.dhis2hiv.usescases.teiDashboard.TeiDashboardComponent;
import org.dhis2hiv.usescases.teiDashboard.TeiDashboardModule;
import org.dhis2hiv.usescases.teiDashboard.nfcdata.NfcDataWriteComponent;
import org.dhis2hiv.usescases.teiDashboard.nfcdata.NfcDataWriteModule;
import org.dhis2hiv.usescases.teiDashboard.teiProgramList.TeiProgramListComponent;
import org.dhis2hiv.usescases.teiDashboard.teiProgramList.TeiProgramListModule;
import org.dhis2hiv.utils.optionset.OptionSetComponent;
import org.dhis2hiv.utils.optionset.OptionSetModule;

import dagger.Subcomponent;

@PerUser
@Subcomponent(modules = UserModule.class)
public interface UserComponent {

    @NonNull
    MainComponent plus(@NonNull MainModule mainModule);


    @NonNull
    ProgramEventDetailComponent plus(@NonNull ProgramEventDetailModule programEventDetailModule);


    @NonNull
    SearchTEComponent plus(@NonNull SearchTEModule searchTEModule);

    @NonNull
    TeiDashboardComponent plus(@NonNull TeiDashboardModule dashboardModule);

    @NonNull
    QrComponent plus(@NonNull QrModule qrModule);

    @NonNull
    QrEventsWORegistrationComponent plus(@NonNull QrEventsWORegistrationModule qrModule);

    @NonNull
    TeiProgramListComponent plus(@NonNull TeiProgramListModule teiProgramListModule);

    @NonNull
    ProgramComponent plus(@NonNull ProgramModule programModule);

    @NonNull
    EventInitialComponent plus(EventInitialModule eventInitialModule);

    @NonNull
    EventSummaryComponent plus(EventSummaryModule eventInitialModule);

    @NonNull
    SyncManagerComponent plus(SyncManagerModule syncManagerModule);

    @NonNull
    ProgramStageSelectionComponent plus(ProgramStageSelectionModule programStageSelectionModule);

    @NonNull
    QrReaderComponent plus(QrReaderModule qrReaderModule);

    @NonNull
    AboutComponent plus(AboutModule aboutModule);

    @NonNull
    DataSetDetailComponent plus(DataSetDetailModule dataSetDetailModel);

    @NonNull
    DataSetInitialComponent plus(DataSetInitialModule dataSetInitialModule);

    @NonNull
    DataSetTableComponent plus(DataSetTableModule dataSetTableModule);

    @NonNull
    DataValueComponent plus(DataValueModule dataValueModule);

    @NonNull
    ReservedValueComponent plus(ReservedValueModule reservedValueModule);

    @NonNull
    SyncDataWorkerComponent plus(SyncDataWorkerModule syncDataWorkerModule);

    @NonNull
    SyncMetadataWorkerComponent plus(SyncMetadataWorkerModule syncDataWorkerModule);

    @NonNull
    ReservedValuesWorkerComponent plus(ReservedValuesWorkerModule reservedValuesWorkerModule);

    @NonNull
    EventCaptureComponent plus(EventCaptureModule eventCaptureModule);

    @NonNull
    SmsComponent plus(SmsModule smsModule);

    NfcDataWriteComponent plus(NfcDataWriteModule nfcModule);

    @NonNull
    SyncGranularRxComponent plus(SyncGranularRxModule syncGranularRxModule);

    @NonNull
    SyncComponent plus(SyncModule syncModule);

    @NonNull
    SyncInitWorkerComponent plus(SyncInitWorkerModule syncInitWorkerModule);

    @NonNull
    EnrollmentComponent plus(EnrollmentModule enrollmentModule);

    @NonNull
    ScheduledEventComponent plus(ScheduledEventModule scheduledEventModule);

    @NonNull
    OptionSetComponent plus(OptionSetModule optionSetModule);

    @NonNull
    NotesComponent plus(NotesModule notesModule);

    @NonNull
    NoteDetailComponent plus(NoteDetailModule noteDetailModule);

    @NonNull
    OUTreeComponent plus(OUTreeModule ouTreeModule);

    @NonNull
    ProgramSettingsComponent plus(SettingsProgramModule settingsProgramModule);

    @NonNull
    ScanComponent plus(ScanModule scanModule);
}
