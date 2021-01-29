package org.dhis2hiv.usescases.teiDashboard.dashboardfragments.indicators

import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import org.dhis2hiv.data.dagger.PerFragment
import org.dhis2hiv.data.forms.FormRepository
import org.dhis2hiv.data.forms.dataentry.EnrollmentRuleEngineRepository
import org.dhis2hiv.data.forms.dataentry.RuleEngineRepository
import org.dhis2hiv.data.schedulers.SchedulerProvider
import org.dhis2hiv.usescases.teiDashboard.DashboardRepository
import org.hisp.dhis.android.core.D2

@PerFragment
@Module
class IndicatorsModule(
    val programUid: String,
    val teiUid: String,
    val view: IndicatorsView
) {

    @Provides
    @PerFragment
    fun providesPresenter(
        d2: D2,
        dashboardRepository: DashboardRepository,
        ruleEngineRepository: RuleEngineRepository,
        schedulerProvider: SchedulerProvider
    ): IndicatorsPresenter {
        return IndicatorsPresenter(
            d2,
            programUid, teiUid,
            dashboardRepository,
            ruleEngineRepository,
            schedulerProvider, view
        )
    }

    @Provides
    @PerFragment
    fun ruleEngineRepository(
        @NonNull formRepository: FormRepository,
        d2: D2
    ): RuleEngineRepository {
        var enrollmentRepository = d2.enrollmentModule()
            .enrollments().byTrackedEntityInstance().eq(teiUid)
        if (programUid.isNotEmpty()) {
            enrollmentRepository = enrollmentRepository.byProgram().eq(programUid)
        }

        val uid = enrollmentRepository.one().blockingGet().uid()
        return EnrollmentRuleEngineRepository(formRepository, uid, d2)
    }
}
