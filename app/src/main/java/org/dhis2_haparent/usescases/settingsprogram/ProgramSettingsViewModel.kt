package org.dhis2_haparent.usescases.settingsprogram

import org.hisp.dhis.android.core.settings.ProgramSetting

data class ProgramSettingsViewModel(
    val programSettings: ProgramSetting,
    val icon: String?,
    val color: String?
)
