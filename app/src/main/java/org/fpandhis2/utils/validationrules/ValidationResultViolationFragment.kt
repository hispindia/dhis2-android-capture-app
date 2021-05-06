package org.fpandhis2.utils.validationrules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.fpandhis2.R
import org.fpandhis2.databinding.FragmentValidationResultViolationBinding
import org.fpandhis2.databinding.ItemDataReviewBinding

class ValidationResultViolationFragment : Fragment() {

    private lateinit var binding: FragmentValidationResultViolationBinding
    private lateinit var violation: Violation

    companion object {
        @JvmStatic
        fun create(): ValidationResultViolationFragment {
            return ValidationResultViolationFragment()
        }
    }

    fun setViolation(violation: Violation) {
        this.violation = violation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_validation_result_violation,
            container,
            false
        )

        binding.apply {
            if (!violation.description.isNullOrEmpty()) {
                description.text = violation.description
                if (!violation.instruction.isNullOrEmpty()) {
                    instruction.text = violation.instruction
                } else {
                    instruction.visibility = View.GONE
                }
            } else if (!violation.instruction.isNullOrEmpty()) {
                description.text = violation.instruction
            } else {
                description.visibility = View.GONE
                instruction.text = getString(R.string.validation_rules_empty_description)
            }
        }

        binding.dataToReviewContainer.removeAllViews()
        for (data in violation.dataToReview) {
            binding.dataToReviewContainer.addView(
                ItemDataReviewBinding.inflate(layoutInflater).apply {
                    dataPosition.text = String.format(
                        "%s | %s",
                        data.dataElementDisplayName,
                        data.categoryOptionComboDisplayName
                    )
                    dataValue.text = data.value
                }.root
            )
        }

        return binding.root
    }
}
