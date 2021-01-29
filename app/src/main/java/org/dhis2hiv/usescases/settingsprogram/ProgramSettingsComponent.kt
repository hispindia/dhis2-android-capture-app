package org.dhis2hiv.usescases.settingsprogram

import dagger.Subcomponent

@Subcomponent(modules = [SettingsProgramModule::class])
interface ProgramSettingsComponent {
    fun inject(activity: SettingsProgramActivity)
}
