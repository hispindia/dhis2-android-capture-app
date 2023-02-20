package org.dhis2_haparent.utils.customviews;

import org.dhis2_haparent.databinding.ItemLoadingBinding;

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
