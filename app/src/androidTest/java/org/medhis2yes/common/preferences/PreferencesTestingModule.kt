package org.medhis2yes.common.preferences

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import org.medhis2yes.data.prefs.PreferenceModule
import org.medhis2yes.data.prefs.PreferenceProvider

@Module
class PreferencesTestingModule : PreferenceModule() {

    @Provides
    @Singleton
    override fun preferenceProvider(context: Context): PreferenceProvider {
        return PreferenceTestingImpl(context)
    }
}
