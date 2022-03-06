package org.dhis2.usescases.about

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.mapbox.mapboxsdk.Mapbox
import kotlinx.android.synthetic.main.activity_about_policy.view.menu
import org.dhis2.R
import org.dhis2.databinding.ActivityAboutAlcoholBinding
import org.dhis2.databinding.ActivityAboutNutritionBinding
import org.dhis2.databinding.ActivityAboutPolicyBinding
import org.dhis2.usescases.general.ActivityGlobalAbstract
import org.hisp.dhis.android.core.D2Manager

class AlcoholView : ActivityGlobalAbstract() {

    private lateinit var binding: ActivityAboutAlcoholBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val settings: SharedPreferences =
            Mapbox.getApplicationContext().getSharedPreferences("lang_uid", 0)
        var homeScore1 = settings.getString("lang", 0.toString()).toString()
        if(homeScore1.equals("burmese"))
        {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_about_alcohol)
            initToolbar()
            binding.webviewPolicy.loadUrl(policyAssets_bur)
        }
        else
        {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_about_alcohol)
            initToolbar()
            binding.webviewPolicy.loadUrl(policyAssets)
        }
        binding.btnDe.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }

    private fun initToolbar() {
        binding.menu.setOnClickListener { onBackPressed() }
        val settings: SharedPreferences =
            Mapbox.getApplicationContext().getSharedPreferences("lang_uid", 0)
        var homeScore1 = settings.getString("lang", 0.toString()).toString()
        if(homeScore1.equals("burmese"))
        {
            binding.toolbarText.text = "အရက်သောက်သုံးခြင်းဆိုင်ရာ အကြံပေးချက်"
        }
        else
        {
            binding.toolbarText.text = getString(R.string.alcohol_title)
        }

    }

    companion object {
            const val policyAssets = "file:///android_asset/alcohol.html"
            const val policyAssets_bur = "file:///android_asset/alcohol_bur.html"

    }
}
