package org.dhis2afgamis.common.preferences

import org.dhis2afgamis.data.prefs.PreferenceProvider

class PreferencesRobot(private val preferences: PreferenceProvider) {

    fun saveValue(key: String, value: Any? = null) {
        preferences.setValue(key, value)
    }

    fun cleanPreferences() {
        preferences.clear()
    }
}
