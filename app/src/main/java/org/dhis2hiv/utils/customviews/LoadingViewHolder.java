package org.dhis2hiv.utils.customviews;

import org.dhis2hiv.databinding.ItemLoadingBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by frodriguez on 5/20/2019.
 */
public class LoadingViewHolder extends RecyclerView.ViewHolder {
    public LoadingViewHolder(@NonNull ItemLoadingBinding itemView) {
        super(itemView.getRoot());
    }

    public void bind(){
    }
}
