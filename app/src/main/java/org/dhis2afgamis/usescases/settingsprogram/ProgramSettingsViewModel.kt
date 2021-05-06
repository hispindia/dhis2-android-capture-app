package org.dhis2afgamis.usescases.settingsprogram

import org.hisp.dhis.android.core.settings.ProgramSetting

data class ProgramSettingsViewModel(
    val programSettings: ProgramSetting,
    val icon: String?,
    val color: String?
)
