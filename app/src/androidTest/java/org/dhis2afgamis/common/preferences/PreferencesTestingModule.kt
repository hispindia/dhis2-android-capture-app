package org.dhis2afgamis.common.preferences

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import org.dhis2afgamis.data.prefs.PreferenceModule
import org.dhis2afgamis.data.prefs.PreferenceProvider

@Module
class PreferencesTestingModule : PreferenceModule() {

    @Provides
    @Singleton
    override fun preferenceProvider(context: Context): PreferenceProvider {
        return PreferenceTestingImpl(context)
    }
}
