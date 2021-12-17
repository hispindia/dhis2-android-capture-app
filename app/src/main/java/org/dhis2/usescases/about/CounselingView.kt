package org.dhis2.usescases.about

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.button.MaterialButton
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
