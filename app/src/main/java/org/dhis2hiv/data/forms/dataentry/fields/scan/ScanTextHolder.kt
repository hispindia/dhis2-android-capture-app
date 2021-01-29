package org.dhis2hiv.data.forms.dataentry.fields.scan

import androidx.lifecycle.MutableLiveData
import io.reactivex.processors.FlowableProcessor
import org.dhis2hiv.data.forms.dataentry.fields.FormViewHolder
import org.dhis2hiv.data.forms.dataentry.fields.RowAction
import org.dhis2hiv.databinding.FormScanBinding
import org.dhis2hiv.utils.customviews.ScanTextView

class ScanTextHolder(
    binding: FormScanBinding,
    val processor: FlowableProcessor<RowAction>,
    val isSearchMode: Boolean,
    val currentSelection: MutableLiveData<String>?
) : FormViewHolder(binding) {

    init {
        this.currentUid = currentSelection
    }

    private lateinit var model: ScanTextViewModel
    private var scanTextView: ScanTextView = binding.scanTextView

    fun update(viewModel: ScanTextViewModel) {
        this.model = viewModel
        label = StringBuilder().append(viewModel.label())
        fieldUid = viewModel.uid()
        scanTextView.run {
            setText(model.value())
            setRenderingType(model.fieldRendering?.type())
            setLabel(model.label(), model.mandatory())
            setHint(model.hint)
            setDescription(model.description())
            setAlert(model.warning(), model.error())
            updateEditable(model.editable() ?: false)
            optionSet = model.optionSet()
            setOnScannerListener { value ->
                this.setText(value)
                if (!isSearchMode) {
                    clearCurrentSelection()
                }
                val rowAction = RowAction.create(
                    model.uid(), value,
                    adapterPosition
                )
                processor.onNext(rowAction)
            }
            setActivationListener {
                setSelectedBackground(isSearchMode)
            }
        }
        initFieldFocus()
    }
}
