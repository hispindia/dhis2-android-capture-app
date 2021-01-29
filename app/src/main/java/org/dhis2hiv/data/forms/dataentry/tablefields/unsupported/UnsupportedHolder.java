package org.dhis2hiv.data.forms.dataentry.tablefields.unsupported;

import android.widget.Button;

import org.dhis2hiv.R;
import org.dhis2hiv.data.forms.dataentry.tablefields.FormViewHolder;
import org.dhis2hiv.databinding.FormUnsupportedCellBinding;


public class UnsupportedHolder extends FormViewHolder {

    private final Button button;

    public UnsupportedHolder(FormUnsupportedCellBinding binding) {
        super(binding);
        button = binding.formButton;
    }

    @Override
    public void dispose() {

    }


    public void update(UnsupportedViewModel viewModel) {
        button.setText(R.string.unsupported_value_type);
        button.setEnabled(false);
        button.setActivated(false);
        descriptionText = viewModel.description();
    }
}
