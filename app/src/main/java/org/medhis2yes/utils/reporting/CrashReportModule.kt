package org.medhis2yes.utils.reporting

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class CrashReportModule internal constructor() {

    @Provides
    @Singleton
    fun provideCrashReportController(): CrashReportController {
        return CrashReportControllerImpl()
    }
}
