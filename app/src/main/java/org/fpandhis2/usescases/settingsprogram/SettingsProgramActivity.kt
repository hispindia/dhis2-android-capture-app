package org.fpandhis2.usescases.settingsprogram

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.toolbar.view.*
import javax.inject.Inject
import org.fpandhis2.Bindings.app
import org.fpandhis2.R
import org.fpandhis2.databinding.ActivitySettingsProgramBinding
import org.fpandhis2.usescases.general.ActivityGlobalAbstract

class SettingsProgramActivity : ActivityGlobalAbstract(), ProgramSettingsView {

    @Inject
    lateinit var adapter: SettingsProgramAdapter
    private lateinit var binding: ActivitySettingsProgramBinding
    @Inject
    lateinit var presenter: SettingsProgramPresenter

    companion object {
        fun getIntentActivity(context: Context): Intent {
            return Intent(context, SettingsProgramActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        app().userComponent()?.plus(SettingsProgramModule(this))?.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_settings_program
        )
        binding.toolbar.title = getString(R.string.activity_program_settings)
        binding.programSettingsView.adapter = adapter
        binding.toolbar.moreOptions.moreOptions.visibility = View.GONE
        binding.toolbar.menu.setOnClickListener { finish() }
        presenter.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }

    override fun setData(programSettings: List<ProgramSettingsViewModel>) {
        adapter.submitList(programSettings)
    }
}
