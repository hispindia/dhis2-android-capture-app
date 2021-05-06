package org.fpandhis2.common.preferences

import org.fpandhis2.data.prefs.PreferenceProvider

class PreferencesRobot(private val preferences: PreferenceProvider) {

    fun saveValue(key: String, value: Any? = null) {
        preferences.setValue(key, value)
    }

    fun cleanPreferences() {
        preferences.clear()
    }
}
