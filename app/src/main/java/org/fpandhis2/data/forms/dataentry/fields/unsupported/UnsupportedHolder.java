package org.fpandhis2.data.forms.dataentry.fields.unsupported;

import org.fpandhis2.data.forms.dataentry.fields.FormViewHolder;
import org.fpandhis2.databinding.FormUnsupportedCustomBinding;
import org.fpandhis2.utils.customviews.UnsupportedView;


public class UnsupportedHolder extends FormViewHolder {

    private final UnsupportedView unsupportedView;

    public UnsupportedHolder(FormUnsupportedCustomBinding binding) {
        super(binding);
        unsupportedView = binding.unsupportedView;
    }

    public void update(UnsupportedViewModel viewModel) {
        unsupportedView.setLabel(viewModel.label());
        descriptionText = viewModel.description();
        label = new StringBuilder().append(viewModel.label());
    }
}
