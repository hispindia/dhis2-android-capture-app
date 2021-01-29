package org.dhis2hiv.data.fingerprint

import android.content.Context
import co.infinum.goldfinger.rx.RxGoldfinger
import dagger.Module
import dagger.Provides
import org.dhis2hiv.BuildConfig
import org.dhis2hiv.data.dagger.PerActivity

@Module
@PerActivity
object FingerPrintModule {

    @JvmStatic
    @Provides
    @PerActivity
    fun provideFingerPrintController(goldfinger: RxGoldfinger, mapper: FingerPrintMapper):
        FingerPrintController {
            return FingerPrintControllerImpl(goldfinger, mapper)
        }

    @JvmStatic
    @Provides
    fun provideFingerPrintModule(context: Context): RxGoldfinger {
        return RxGoldfinger.Builder(context).logEnabled(BuildConfig.DEBUG).build()
    }

    @JvmStatic
    @Provides
    fun provideFingerPrintMapper(): FingerPrintMapper {
        return FingerPrintMapper()
    }
}
