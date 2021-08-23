package org.medhis2yes.common.db

import android.os.Environment
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.medhis2yes.AppTest
import org.medhis2yes.data.server.ServerModule
import org.medhis2yes.usescases.BaseTest
import org.hisp.dhis.android.core.D2Manager
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@RunWith(AndroidJUnit4::class)
class TestingDatabase : BaseTest() {

    companion object {
        const val url = "https://play.medhis2yes.org/android-current/"
        const val username = "android"
        const val password = "Android123"
    }

    @Test
    @Ignore
    fun copyDatabase() {

        /* Download db */
        val d2 = D2Manager.blockingInstantiateD2(ServerModule.getD2Configuration(ApplicationProvider.getApplicationContext<AppTest>()))
        d2.userModule()
            .logIn(username, password, url)
            .blockingGet()
        d2.metadataModule().blockingDownload()

        /* Export Db to sdcard */
        try {
            val sd = Environment.getExternalStorageDirectory()

            if (sd.canWrite()) {
                val currentDB =
                    context.getDatabasePath("play-medhis2yes-org-android-current_android_unencrypted.db")
                val backupDBPath = "dhis_test.db"
                val backupDB = File(sd, backupDBPath)

                if (currentDB.exists()) {
                    val src = FileInputStream(currentDB).channel
                    val dst = FileOutputStream(backupDB).channel
                    dst.transferFrom(src, 0, src.size())
                    src.close()
                    dst.close()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}