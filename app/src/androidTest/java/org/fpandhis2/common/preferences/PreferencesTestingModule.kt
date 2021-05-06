package org.fpandhis2.common.preferences

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import org.fpandhis2.data.prefs.PreferenceModule
import org.fpandhis2.data.prefs.PreferenceProvider

@Module
class PreferencesTestingModule : PreferenceModule() {

    @Provides
    @Singleton
    override fun preferenceProvider(context: Context): PreferenceProvider {
        return PreferenceTestingImpl(context)
    }
}
