package org.dhis2.usescases.about

import android.content.SharedPreferences
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.mapbox.mapboxsdk.Mapbox
import kotlinx.android.synthetic.main.activity_about_policy.view.menu
import org.dhis2.R
import org.dhis2.databinding.ActivityAboutAlcoholBinding
import org.dhis2.databinding.ActivityAboutDiabBinding
import org.dhis2.databinding.ActivityAboutNutritionBinding
import org.dhis2.databinding.ActivityAboutPolicyBinding
import org.dhis2.usescases.general.ActivityGlobalAbstract

class DiabeticView : ActivityGlobalAbstract() {

    private lateinit var binding: ActivityAboutDiabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settings: SharedPreferences =
            Mapbox.getApplicationContext().getSharedPreferences("lang_uid", 0)
        var homeScore1 = settings.getString("lang", 0.toString()).toString()
        if(homeScore1.equals("burmese"))
        {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_about_diab)
            initToolbar()
            binding.webviewPolicy.loadUrl(AlcoholView.policyAssets_bur)
        }
        else
        {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_about_diab)
            initToolbar()
            binding.webviewPolicy.loadUrl(policyAssets)
        }


    }

    private fun initToolbar() {
        binding.menu.setOnClickListener { onBackPressed() }
        val settings: SharedPreferences =
            Mapbox.getApplicationContext().getSharedPreferences("lang_uid", 0)
        var homeScore1 = settings.getString("lang", 0.toString()).toString()
        if(homeScore1.equals("burmese"))
        {
            binding.toolbarText.text = "ဆီးချိုဝေဒနာရှင်များအတွက်"
        }
        else
        {
            binding.toolbarText.text = getString(R.string.diab_title)
        }


    }

    companion object {
        const val policyAssets = "file:///android_asset/diabetes.html"
        const val policyAssets_bur = "file:///android_asset/diabetes_bur.html"
    }
}
