package org.fpandhis2.data.forms.dataentry.tablefields.unsupported;

import android.widget.Button;

import org.fpandhis2.R;
import org.fpandhis2.data.forms.dataentry.tablefields.FormViewHolder;
import org.fpandhis2.databinding.FormUnsupportedCellBinding;


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
