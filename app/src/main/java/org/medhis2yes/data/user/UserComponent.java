package org.medhis2yes.data.user;

import androidx.annotation.NonNull;

import org.medhis2yes.data.dagger.PerUser;
import org.medhis2yes.data.filter.FilterPresenter;
import org.medhis2yes.data.service.ReservedValuesWorkerComponent;
import org.medhis2yes.data.service.ReservedValuesWorkerModule;
import org.medhis2yes.data.service.SyncDataWorkerComponent;
import org.medhis2yes.data.service.SyncDataWorkerModule;
import org.medhis2yes.data.service.SyncGranularRxComponent;
import org.medhis2yes.data.service.SyncGranularRxModule;
import org.medhis2yes.data.service.SyncInitWorkerComponent;
import org.medhis2yes.data.service.SyncInitWorkerModule;
import org.medhis2yes.data.service.SyncMetadataWorkerComponent;
import org.medhis2yes.data.service.SyncMetadataWorkerModule;
import org.medhis2yes.usescases.about.AboutComponent;
import org.medhis2yes.usescases.about.AboutModule;
import org.medhis2yes.usescases.datasets.dataSetTable.DataSetTableComponent;
import org.medhis2yes.usescases.datasets.dataSetTable.DataSetTableModule;
import org.medhis2yes.usescases.datasets.dataSetTable.dataSetSection.DataValueComponent;
import org.medhis2yes.usescases.datasets.dataSetTable.dataSetSection.DataValueModule;
import org.medhis2yes.usescases.datasets.datasetDetail.DataSetDetailComponent;
import org.medhis2yes.usescases.datasets.datasetDetail.DataSetDetailModule;
import org.medhis2yes.usescases.datasets.datasetInitial.DataSetInitialComponent;
import org.medhis2yes.usescases.datasets.datasetInitial.DataSetInitialModule;
import org.medhis2yes.usescases.enrollment.EnrollmentComponent;
import org.medhis2yes.usescases.enrollment.EnrollmentModule;
import org.medhis2yes.usescases.events.ScheduledEventComponent;
import org.medhis2yes.usescases.events.ScheduledEventModule;
import org.medhis2yes.usescases.eventsWithoutRegistration.eventCapture.EventCaptureComponent;
import org.medhis2yes.usescases.eventsWithoutRegistration.eventCapture.EventCaptureModule;
import org.medhis2yes.usescases.eventsWithoutRegistration.eventInitial.EventInitialComponent;
import org.medhis2yes.usescases.eventsWithoutRegistration.eventInitial.EventInitialModule;
import org.medhis2yes.usescases.main.MainComponent;
import org.medhis2yes.usescases.main.MainModule;
import org.medhis2yes.usescases.main.program.ProgramComponent;
import org.medhis2yes.usescases.main.program.ProgramModule;
import org.medhis2yes.usescases.notes.NotesComponent;
import org.medhis2yes.usescases.notes.NotesModule;
import org.medhis2yes.usescases.notes.noteDetail.NoteDetailComponent;
import org.medhis2yes.usescases.notes.noteDetail.NoteDetailModule;
import org.medhis2yes.usescases.programEventDetail.ProgramEventDetailComponent;
import org.medhis2yes.usescases.programEventDetail.ProgramEventDetailModule;
import org.medhis2yes.usescases.programStageSelection.ProgramStageSelectionComponent;
import org.medhis2yes.usescases.programStageSelection.ProgramStageSelectionModule;
import org.medhis2yes.usescases.qrCodes.QrComponent;
import org.medhis2yes.usescases.qrCodes.QrModule;
import org.medhis2yes.usescases.qrCodes.eventsworegistration.QrEventsWORegistrationComponent;
import org.medhis2yes.usescases.qrCodes.eventsworegistration.QrEventsWORegistrationModule;
import org.medhis2yes.usescases.qrReader.QrReaderComponent;
import org.medhis2yes.usescases.qrReader.QrReaderModule;
import org.medhis2yes.usescases.qrScanner.ScanComponent;
import org.medhis2yes.usescases.qrScanner.ScanModule;
import org.medhis2yes.usescases.reservedValue.ReservedValueComponent;
import org.medhis2yes.usescases.reservedValue.ReservedValueModule;
import org.medhis2yes.usescases.searchTrackEntity.SearchTEComponent;
import org.medhis2yes.usescases.searchTrackEntity.SearchTEModule;
import org.medhis2yes.usescases.settings.SyncManagerComponent;
import org.medhis2yes.usescases.settings.SyncManagerModule;
import org.medhis2yes.usescases.settingsprogram.ProgramSettingsComponent;
import org.medhis2yes.usescases.settingsprogram.SettingsProgramModule;
import org.medhis2yes.usescases.sms.SmsComponent;
import org.medhis2yes.usescases.sms.SmsModule;
import org.medhis2yes.usescases.sync.SyncComponent;
import org.medhis2yes.usescases.sync.SyncModule;
import org.medhis2yes.usescases.teiDashboard.TeiDashboardComponent;
import org.medhis2yes.usescases.teiDashboard.TeiDashboardModule;
import org.medhis2yes.usescases.teiDashboard.nfcdata.NfcDataWriteComponent;
import org.medhis2yes.usescases.teiDashboard.nfcdata.NfcDataWriteModule;
import org.medhis2yes.usescases.teiDashboard.teiProgramList.TeiProgramListComponent;
import org.medhis2yes.usescases.teiDashboard.teiProgramList.TeiProgramListModule;
import org.medhis2yes.utils.optionset.OptionSetComponent;
import org.medhis2yes.utils.optionset.OptionSetModule;

import dagger.Subcomponent;

@PerUser
@Subcomponent(modules = UserModule.class)
public interface UserComponent {

    FilterPresenter filterPresenter();

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
    ProgramSettingsComponent plus(SettingsProgramModule settingsProgramModule);

    @NonNull
    ScanComponent plus(ScanModule scanModule);
}
