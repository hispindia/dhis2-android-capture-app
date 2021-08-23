package org.medhis2yes.usescases.sync

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import javax.inject.Inject
import org.medhis2yes.App
import org.medhis2yes.Bindings.Bindings
import org.medhis2yes.Bindings.drawableFrom
import org.medhis2yes.Bindings.userComponent
import org.medhis2yes.R
import org.medhis2yes.databinding.ActivitySynchronizationBinding
import org.medhis2yes.usescases.general.ActivityGlobalAbstract
import org.medhis2yes.usescases.login.LoginActivity
import org.medhis2yes.usescases.main.MainActivity
import org.medhis2yes.utils.OnDialogClickListener
import org.medhis2yes.utils.extension.navigateTo
import org.medhis2yes.utils.extension.share

class SyncActivity : ActivityGlobalAbstract(), SyncView {

    lateinit var binding: ActivitySynchronizationBinding

    @Inject
    lateinit var presenter: SyncPresenter

    @Inject
    lateinit var animations: SyncAnimations

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
        navigateTo<MainActivity>(true, flagsToApply = Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun goToLogin() {
        navigateTo<LoginActivity>(true, flagsToApply = Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}
