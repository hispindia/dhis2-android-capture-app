package org.dhis2.usescases.about

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.button.MaterialButton
import com.mapbox.mapboxsdk.Mapbox
import kotlinx.android.synthetic.main.activity_about_policy.view.menu
import org.dhis2.R
import org.dhis2.databinding.ActivityAboutAlcoholBinding
import org.dhis2.databinding.ActivityAboutCounselingBinding
import org.dhis2.databinding.ActivityAboutNutritionBinding
import org.dhis2.databinding.ActivityAboutPolicyBinding
import org.dhis2.usescases.general.ActivityGlobalAbstract

class CounselingView : ActivityGlobalAbstract() {

    private lateinit var binding: ActivityAboutCounselingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_counseling)
        initToolbar()
        val alcohol_click= findViewById<MaterialButton>(R.id.alcohol)
        val physical_click= findViewById<MaterialButton>(R.id.physical)
        val mental_click= findViewById<MaterialButton>(R.id.mental)
        val tobaco_click= findViewById<MaterialButton>(R.id.tobacco)
        val nutrition_click= findViewById<MaterialButton>(R.id.nutrition)

        val settings: SharedPreferences =
            Mapbox.getApplicationContext().getSharedPreferences("lang_uid", 0)
        var homeScore1 = settings.getString("lang", 0.toString()).toString()
        if(homeScore1.equals("burmese"))
        {
            alcohol_click.setText("အရက်သောက်သုံးခြင်းဆိုင်ရာ အကြံပေးချက်")
            physical_click.setText("ကိုယ်လက်လှုပ်ရှားမှုဆိုင်ရာ အကြံပေးချက်")
            mental_click.setText("စိတ်ကျန်းမာရေးဆိုင်ရာ အကြံဉာဏ်ပေးချက်")
            tobaco_click.setText("ဆေးလိပ်/ဆေးရွက်ကြီးသောက်သုံးမှုဆိုင်ရာ အကြံပေးချက်")
            nutrition_click.setText("အစာအာဟာရဆိုင်ရာအကြံပေးချက်")
        }


        alcohol_click?.setOnClickListener {
            val intent = Intent(context, AlcoholView::class.java)
            context.startActivity(intent)

        }
        physical_click?.setOnClickListener {
            val intent = Intent(context, PhysicalView::class.java)
            context.startActivity(intent)

        }
        mental_click?.setOnClickListener {
            val intent = Intent(context, MentalView::class.java)
            context.startActivity(intent)

        }
        tobaco_click?.setOnClickListener {
            val intent = Intent(context, TobaccoView::class.java)
            context.startActivity(intent)

        }
        nutrition_click?.setOnClickListener {
            val intent = Intent(context, NutritionView::class.java)
            context.startActivity(intent)

        }
    }

    private fun initToolbar() {
        binding.menu.setOnClickListener { onBackPressed() }
        binding.toolbarText.text = getString(R.string.counseling_title)
    }

}
