package org.dhis2hiv.common.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import org.dhis2hiv.common.keystore.KeyStoreRobot
import org.dhis2hiv.common.preferences.PreferenceTestingImpl
import org.dhis2hiv.common.preferences.PreferencesRobot
import org.hisp.dhis.android.core.arch.storage.internal.AndroidSecureStore

@Module
class BaseTestModule(val context: Context) {

    @Provides
    @Singleton
    fun providesKeystoreRobot(): KeyStoreRobot {
        return KeyStoreRobot(AndroidSecureStore(context))
    }

    @Provides
    @Singleton
    fun providesPreferencesRobot(): PreferencesRobot {
        return PreferencesRobot(PreferenceTestingImpl(context))
    }
}
