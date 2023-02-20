package org.dhis2_haparent.common.di

import android.content.Context
import org.dhis2_haparent.DBTestLoader
import org.dhis2_haparent.common.FileReader
import org.dhis2_haparent.common.keystore.KeyStoreRobot
import org.dhis2_haparent.common.mockwebserver.MockWebServerRobot
import org.dhis2_haparent.common.preferences.PreferenceTestingImpl
import org.dhis2_haparent.common.preferences.PreferencesRobot
import org.hisp.dhis.android.core.arch.storage.internal.AndroidSecureStore
import org.hisp.dhis.android.core.mockwebserver.Dhis2MockServer

class TestingInjector {

    companion object {
        private const val CONFIG_FILE = "smsconfig"

        private var keystore: AndroidSecureStore? = null

        fun providesKeyStoreRobot(context: Context): KeyStoreRobot {
             keystore = AndroidSecureStore(context)
             return KeyStoreRobot(AndroidSecureStore(context))
        }
        fun providesPreferencesRobot(context: Context): PreferencesRobot {
            return PreferencesRobot(PreferenceTestingImpl(context),
                context.getSharedPreferences(
                    CONFIG_FILE,
                    Context.MODE_PRIVATE
                ))
        }
        fun providesMockWebserverRobot(context: Context): MockWebServerRobot {
            return MockWebServerRobot(Dhis2MockServer(FileReader(context), 8080))
        }
        fun provideDBImporter(context: Context): DBTestLoader {
            return DBTestLoader(context)
        }
        fun getStorage(): AndroidSecureStore {
            return keystore!!
        }
    }
}
