package org.dhis2.data.forms.dataentry.fields.section

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.Observable
import kotlinx.android.synthetic.main.section_view.view.*
import org.dhis2.Bindings.dp
import org.dhis2.R
import org.dhis2.commons.dialogs.CustomDialog
import org.dhis2.databinding.SectionViewBinding
import org.dhis2.utils.customviews.FieldLayout
import org.hisp.dhis.android.core.D2Manager

class SectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FieldLayout(context, attrs, defStyleAttr) {

    private lateinit var sectionViewModel: SectionViewModel
    private lateinit var binding: SectionViewBinding

    private fun initLayout() {
        if (!::binding.isInitialized) {
            binding = SectionViewBinding.inflate(LayoutInflater.from(context), this, true)
        }
    }

    fun setViewModel(sectionViewModel: SectionViewModel) {
        this.sectionViewModel = sectionViewModel
        initLayout()

        sectionViewModel.selectedField()
            ?.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(
                    sender: Observable,
                    propertyId: Int
                ) {
                    setShadow(sectionViewModel.isOpen)

                    if (sectionViewModel.uid == "XzDycWaOWDo") {
                        shadow_top.setImageResource(R.drawable.smoking);
                    }
                    else if (sectionViewModel.uid == "MBUILXnzoZ3") {
                        shadow_top.setImageResource(R.drawable.alcohol_2);

                    }
                    else if (sectionViewModel.uid == "OgWPltR2l6C") {
                        if (D2Manager.getD2().settingModule().userSettings().blockingGet().keyDbLocale() == "en")
                        {
                            shadow_top.setImageResource(R.drawable.physical);
                        }
                       else
                        {
                            shadow_top.setImageResource(R.drawable.physical_my1);
                        }

                    }
                    else if (sectionViewModel.uid == "Cacz6OdL9Pz") {
                        Log.d("rest--","dsads");
//                        last_section_details.visibility= VISIBLE
                        shadow_top.setBackgroundResource(R.drawable.bp)
//                        shadow_top_text.visibility= View.VISIBLE;
//                        descriptionIcon.visibility = View.VISIBLE
//                        descriptionIcon.setImageResource(R.drawable.cvg_sectiob);
//                        shadow_end.setBackgroundColor(R.drawable.cvg_sectiob);
                    }
                    else
                    {

                        shadow_top.setImageResource(R.drawable.shadow);
                    }
//                    else
//                    {
//                        shadow_top.visibility=View.GONE;
//                    }
                    animateArrow(sectionViewModel.isSelected)
                }
            })
        binding.apply {
            item = sectionViewModel

            descriptionIcon.visibility = View.GONE
            sectionName.viewTreeObserver.addOnGlobalLayoutListener {
                sectionName.takeIf { it.text == sectionViewModel.label() }?.layout?.let {
                    val isEllipsized = it.getEllipsisCount(0) > 0
                    if (sectionViewModel.hasToShowDescriptionIcon(isEllipsized)) {
                        descriptionIcon.visibility = View.VISIBLE
                    }
                }
            }
            setShadow(sectionViewModel.isOpen)
            if (sectionViewModel.uid == "XzDycWaOWDo") {
                shadow_top.setImageResource(R.drawable.smoking);
            }
            else if (sectionViewModel.uid == "MBUILXnzoZ3") {
                shadow_top.setImageResource(R.drawable.alcohol_2);
            }
            else if (sectionViewModel.uid == "OgWPltR2l6C") {

                if (D2Manager.getD2().settingModule().userSettings().blockingGet().keyDbLocale() == "en")
                {
                    shadow_top.setImageResource(R.drawable.physical);
                }
                else
                {
                    shadow_top.setImageResource(R.drawable.physical_my1);
                }

            }
            else if (sectionViewModel.uid == "oP52XFH9i3E") {
                shadow_top.setImageResource(R.drawable.bp);
            }
            else
            {
                shadow_top.setImageResource(R.drawable.shadow);
            }

            root.setOnClickListener {
                sectionViewModel.setSelected()
            }
            openIndicator.setOnClickListener {
                sectionViewModel.setSelected()
            }
            descriptionIcon.setOnClickListener {
                showDescription()
            }
            sectionNumber.apply {
                text = sectionViewModel.sectionNumber.toString()
                background =
                    ContextCompat.getDrawable(context, R.drawable.ic_circle)
            }
        }

        setLastSectionHeight(sectionViewModel.lastPositionShouldChangeHeight())
        setBottomShadow(sectionViewModel.showBottomShadow())
        binding.executePendingBindings()
    }

    private fun setShadow(open: Boolean) {
        binding.shadowTop.visibility = if (open) View.VISIBLE else View.GONE
    }

    private fun setBottomShadow(showShadow: Boolean) {
        binding.shadowBottom.visibility = if (showShadow) View.VISIBLE else View.GONE
    }

    private fun showDescription() {
        CustomDialog(
            context,
            sectionViewModel.label(),
            sectionViewModel.description() ?: "",
            context.getString(R.string.action_close),
            null,
            201,
            null
        ).show()
    }

    private fun showDescription1() {
        CustomDialog(
            context,
            sectionViewModel.label(),
            sectionViewModel.description() ?: "",
            context.getString(R.string.action_close),
            "please ensure these details are filled: Gender, Diabetes details, blood pressure (systolic), smoking status and age.",
            201,
            null
        ).show()
    }

    private fun animateArrow(isSelected: Boolean) {
        if (isSelected) {
            binding.openIndicator.scaleY = 1f
        }
        binding.openIndicator.animate()
            .scaleY(if (isSelected) 1f else -1f)
            .setDuration(200)
            .start()
    }

    fun handleHeaderClick(x: Float) {
        val hasDescription = binding.descriptionIcon.visibility == View.VISIBLE
        val descriptionClicked =
            binding.descriptionIcon.x <= x &&
                binding.descriptionIcon.x + binding.descriptionIcon.width >= x
        if (hasDescription && descriptionClicked) {
            showDescription()
        } else {
            sectionViewModel.setSelected()
        }
    }

    private fun setLastSectionHeight(previousSectionIsOpened: Boolean) {
        val params = binding.lastSectionDetails.layoutParams
        val finalHeight = if (previousSectionIsOpened) {
            48.dp
        } else {
            1.dp
        }
        ValueAnimator.ofInt(params.height, finalHeight).apply {
            duration = 120
            addUpdateListener {
                params.height = it.animatedValue as Int
                binding.lastSectionDetails.layoutParams = params
            }
            start()
        }
    }
}
