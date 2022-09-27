package org.dhis2.yesme.usescases.sync

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import com.mapbox.mapboxsdk.Mapbox
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject
import org.dhis2.App
import org.dhis2.yesme.Bindings.Bindings
import org.dhis2.yesme.Bindings.drawableFrom
import org.dhis2.yesme.Bindings.userComponent
import org.dhis2.yesme.R
import org.dhis2.data.ActivityGoTo
import org.dhis2.data.EnrollDemographs
import org.dhis2.yesme.databinding.ActivitySynchronizationBinding
import org.dhis2.yesme.usescases.general.ActivityGlobalAbstract
import org.dhis2.yesme.usescases.login.LoginActivity
import org.dhis2.utils.OnDialogClickListener
import org.dhis2.utils.extension.navigateTo
import org.dhis2.utils.extension.share
import org.dhis2.yesme.usescases.main.MainActivity
import org.hisp.dhis.android.core.D2Manager

class SyncActivity : ActivityGlobalAbstract(), SyncView {

    lateinit var binding: ActivitySynchronizationBinding

    @Inject
    lateinit var presenter: SyncPresenter

    @Inject
    lateinit var animations: SyncAnimations
    private var phone_number: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        val serverComponent = (applicationContext as App).serverComponent()
        userComponent()?.plus(SyncModule(this, serverComponent))?.inject(this) ?: finish()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_synchronization)
        binding.presenter = presenter
        presenter.sync()
    }

    override fun onResume() {
        super.onResume()
        presenter.observeSyncProcess().observe(
            this,
            Observer<List<WorkInfo>> { workInfoList: List<WorkInfo> ->
                presenter.handleSyncInfo(workInfoList)
            }
        )
    }

    override fun setMetadataSyncStarted() {
        Bindings.setDrawableEnd(
            binding.metadataText,
            AppCompatResources.getDrawable(
                this,
                R.drawable.animator_sync
            )
        )
    }

    override fun setMetadataSyncSucceed() {
        binding.metadataText.text = getString(R.string.configuration_ready)
        Bindings.setDrawableEnd(
            binding.metadataText,
            AppCompatResources.getDrawable(
                this,
                R.drawable.animator_done
            )
        )
        presenter.onMetadataSyncSuccess()
    }

    override fun showMetadataFailedMessage(message: String?) {
        showInfoDialog(
            getString(R.string.something_wrong),
            getString(R.string.metada_first_sync_error),
            getString(R.string.share),
            getString(R.string.go_back),
            object : OnDialogClickListener {
                override fun onPositiveClick() {
                    message?.let { share(it) }
                }

                override fun onNegativeClick() {
                    presenter.onLogout()
                }
            }
        )
    }

    override fun setDataSyncStarted() {
        binding.eventsText.apply {
            text = getString(R.string.syncing_data)
            Bindings.setDrawableEnd(this, drawableFrom(R.drawable.animator_sync))
            alpha = 1.0f
        }
    }

    override fun setDataSyncSucceed() {
        binding.eventsText.apply {
            text = getString(R.string.data_ready)
            Bindings.setDrawableEnd(this, drawableFrom(R.drawable.animator_done))
        }
        presenter.onDataSyncSuccess()
    }

    override fun onStart() {
        super.onStart()
        animations.startLottieAnimation(binding.lottieView)
    }

    override fun onStop() {
        binding.lottieView.cancelAnimation()
        presenter.onDetach()
        super.onStop()
    }

    override fun setServerTheme(themeId: Int) {
        animations.startThemeAnimation(this, { super.setTheme(themeId) }) { colorValue ->
            binding.logo.setBackgroundColor(colorValue)
        }
    }

    override fun setFlag(flagName: String?) {
        binding.logoFlag.setImageResource(
            resources.getIdentifier(flagName, "drawable", packageName)
        )
        animations.startFlagAnimation { value: Float? ->
            binding.apply {
                logoFlag.alpha = value!!
                dhisLogo.alpha = 0f
            }
        }
    }

    override fun goToMain() {
//        navigateTo<MainActivity>(true, flagsToApply = Intent.FLAG_ACTIVITY_NEW_TASK)
        val settings: SharedPreferences =
            Mapbox.getApplicationContext().getSharedPreferences("phone_no", 0)

        phone_number = settings.getString("phone", 0.toString()).toString()
        Log.d("test--",phone_number)
        if(phone_number.equals("7982715559"))
        {
            Log.d("test--","done")
            navigateTo<MainActivity>(true, flagsToApply = Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        else
        {
            Log.d("test--","notdone")
            //@Sou on initial sync done
            if(D2Manager.getD2().trackedEntityModule().trackedEntityInstances().blockingGet().size>0)
            {
                val teav = D2Manager.getD2().trackedEntityModule().trackedEntityAttributeValues()
                    .byTrackedEntityInstance().eq(D2Manager.getD2().trackedEntityModule().trackedEntityInstances().blockingGet().get(0).uid()).blockingGet()
                if (teav.size < 2)
                {
                    val mainactivity = Intent(
                        this@SyncActivity,
                        EnrollDemographs::class.java
                    )

                    if(D2Manager.getD2().enrollmentModule().enrollments().blockingGet().size>0)
                    {
                        mainactivity.putExtra("ENROLLMENT_UID_EXTRA", D2Manager.getD2().enrollmentModule().enrollments().blockingGet().get(0).uid()) //Optional parameters

                    }
                    mainactivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    mainactivity.putExtra("PROGRAM_UID_EXTRA", D2Manager.getD2().programModule().programs().blockingGet().get(0).uid())
                    startActivity(mainactivity)
                    finish()
                }
                //@Sou fix for screen stuck at sync after login
                else
                {
                    navigateTo<ActivityGoTo>(true, flagsToApply = Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            }
            else
            {
                val mainactivity = Intent(
                    this@SyncActivity,
                    EnrollDemographs::class.java
                )

                mainactivity.putExtra("ENROLLMENT_UID_EXTRA", D2Manager.getD2().enrollmentModule().enrollments().blockingGet().get(0).uid()) //Optional parameters
                mainactivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mainactivity.putExtra("PROGRAM_UID_EXTRA", D2Manager.getD2().programModule().programs().blockingGet().get(0).uid())
                startActivity(mainactivity)
                finish()
            }
        }
        Log.d("dd---","00")



    }

    override fun goToLogin() {
        navigateTo<LoginActivity>(true, flagsToApply = Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}
