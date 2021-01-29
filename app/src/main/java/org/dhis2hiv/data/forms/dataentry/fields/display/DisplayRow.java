package org.dhis2hiv.data.forms.dataentry.fields.display;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import org.dhis2hiv.R;
import org.dhis2hiv.data.forms.dataentry.fields.Row;
import org.dhis2hiv.databinding.ItemIndicatorBinding;

public class DisplayRow implements Row<DisplayHolder, DisplayViewModel> {

    @NonNull
    private final LayoutInflater inflater;

    public DisplayRow(@NonNull LayoutInflater layoutInflater) {
        this.inflater = layoutInflater;
    }

    @NonNull
    @Override
    public DisplayHolder onCreate(@NonNull ViewGroup parent) {
        ItemIndicatorBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_indicator, parent, false);
        return new DisplayHolder(binding);
    }

    @Override
    public void onBind(@NonNull DisplayHolder viewHolder, @NonNull DisplayViewModel viewModel) {
        viewHolder.update(viewModel);
    }
}
