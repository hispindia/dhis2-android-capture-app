package org.dhis2.usescases.about

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_about_policy.view.menu
import org.dhis2.R
import org.dhis2.databinding.ActivityAboutAlcoholBinding
import org.dhis2.databinding.ActivityAboutMentalBinding
import org.dhis2.databinding.ActivityAboutNutritionBinding
import org.dhis2.databinding.ActivityAboutPolicyBinding
import org.dhis2.usescases.general.ActivityGlobalAbstract

class MentalView : ActivityGlobalAbstract() {

    private lateinit var binding: ActivityAboutMentalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_mental)
        initToolbar()
        binding.webviewPolicy.loadUrl(policyAssets)
    }

    private fun initToolbar() {
        binding.menu.setOnClickListener { onBackPressed() }
        binding.toolbarText.text = getString(R.string.mental_title)
    }

    companion object {
        const val policyAssets = "file:///android_asset/mental.html"
    }
}
