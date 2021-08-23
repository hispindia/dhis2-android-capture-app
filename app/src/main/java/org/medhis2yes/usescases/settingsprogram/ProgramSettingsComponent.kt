package org.medhis2yes.usescases.settingsprogram

import dagger.Subcomponent

@Subcomponent(modules = [SettingsProgramModule::class])
interface ProgramSettingsComponent {
    fun inject(activity: SettingsProgramActivity)
}
