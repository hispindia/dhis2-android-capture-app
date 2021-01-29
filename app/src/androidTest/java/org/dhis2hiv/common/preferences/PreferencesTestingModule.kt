package org.dhis2hiv.common.preferences

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import org.dhis2hiv.data.prefs.PreferenceModule
import org.dhis2hiv.data.prefs.PreferenceProvider

@Module
class PreferencesTestingModule : PreferenceModule() {

    @Provides
    @Singleton
    override fun preferenceProvider(context: Context): PreferenceProvider {
        return PreferenceTestingImpl(context)
    }
}
