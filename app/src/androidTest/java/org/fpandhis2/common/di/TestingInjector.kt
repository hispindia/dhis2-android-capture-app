package org.fpandhis2.common.di

import android.content.Context
import org.fpandhis2.common.FileReader
import org.fpandhis2.common.keystore.KeyStoreRobot
import org.fpandhis2.common.mockwebserver.MockWebServerRobot
import org.fpandhis2.common.preferences.PreferenceTestingImpl
import org.fpandhis2.common.preferences.PreferencesRobot
import org.hisp.dhis.android.core.arch.storage.internal.AndroidSecureStore
import org.hisp.dhis.android.core.mockwebserver.Dhis2MockServer

class TestingInjector {

    companion object {
        fun providesKeyStoreRobot(context: Context): KeyStoreRobot {
            return KeyStoreRobot(AndroidSecureStore(context))
        }
        fun providesPreferencesRobot(context: Context): PreferencesRobot {
            return PreferencesRobot(PreferenceTestingImpl(context))
        }
        fun providesMockWebserverRobot(context: Context): MockWebServerRobot {
            return MockWebServerRobot(Dhis2MockServer(FileReader(context), 8080))
        }
    }
}
