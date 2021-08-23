package org.medhis2yes.usescases.about

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.medhis2yes.R
import org.medhis2yes.databinding.ActivityAboutPolicyBinding
import org.medhis2yes.usescases.general.ActivityGlobalAbstract

class PolicyView : ActivityGlobalAbstract() {

    private lateinit var binding: ActivityAboutPolicyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_policy)
        initToolbar()
        binding.webviewPolicy.loadUrl(policyAssets)
    }

    private fun initToolbar() {
        binding.menu.setOnClickListener { onBackPressed() }
        binding.toolbarText.text = getString(R.string.privacy_policy_title)
    }

    companion object {
        const val policyAssets = "file:///android_asset/policy.html"
    }
}
