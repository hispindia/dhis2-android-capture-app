package org.fpandhis2.usescases.sync

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import javax.inject.Inject
import org.fpandhis2.Bindings.Bindings
import org.fpandhis2.Bindings.drawableFrom
import org.fpandhis2.Bindings.userComponent
import org.fpandhis2.R
import org.fpandhis2.databinding.ActivitySynchronizationBinding
import org.fpandhis2.usescases.general.ActivityGlobalAbstract
import org.fpandhis2.usescases.login.LoginActivity
import org.fpandhis2.usescases.main.MainActivity
import org.fpandhis2.utils.OnDialogClickListener
import org.fpandhis2.utils.extension.navigateTo
import org.fpandhis2.utils.extension.share

class SyncActivity : ActivityGlobalAbstract(), SyncView {

    lateinit var binding: ActivitySynchronizationBinding

    @Inject
    lateinit var presenter: SyncPresenter

    @Inject
    lateinit var animations: SyncAnimations

    override fun onCreate(savedInstanceState: Bundle?) {
        userComponent()?.plus(SyncModule(this))?.inject(this) ?: finish()
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
                    share(message!!)
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
