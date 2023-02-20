package org.dhis2_haparent.usescases.settingsprogram

import dagger.Subcomponent

@Subcomponent(modules = [SettingsProgramModule::class])
interface ProgramSettingsComponent {
    fun inject(activity: SettingsProgramActivity)
}
