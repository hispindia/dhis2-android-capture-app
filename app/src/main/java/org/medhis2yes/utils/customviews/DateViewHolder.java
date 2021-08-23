package org.medhis2yes.utils.customviews;

import androidx.recyclerview.widget.RecyclerView;

import org.medhis2yes.databinding.ItemDateBinding;

public class DateViewHolder extends RecyclerView.ViewHolder {

    private final ItemDateBinding binding;

    public DateViewHolder(ItemDateBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(String date) {
        binding.setDate(date);
        binding.executePendingBindings();
    }
}
