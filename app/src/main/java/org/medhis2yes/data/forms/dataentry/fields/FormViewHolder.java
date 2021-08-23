package org.medhis2yes.data.forms.dataentry.fields;

import android.widget.ImageView;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import org.medhis2yes.BR;
import org.medhis2yes.Bindings.ExtensionsKt;
import org.medhis2yes.Bindings.ViewExtensionsKt;
import org.medhis2yes.R;
import org.hisp.dhis.android.core.common.FeatureType;
import org.medhis2yes.form.model.FieldUiModel;
import org.medhis2yes.form.model.RowAction;
import org.medhis2yes.form.ui.RecyclerViewUiEvents;
import org.medhis2yes.form.ui.intent.FormIntent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FormViewHolder extends RecyclerView.ViewHolder {

    private final ViewDataBinding binding;

    public FormViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        ImageView fieldSelected = binding.getRoot().findViewById(R.id.fieldSelected);
        if (fieldSelected != null) {
            ViewExtensionsKt.clipWithAllRoundedCorners(fieldSelected, ExtensionsKt.getDp(2));
        }
    }

    public void bind(FieldUiModel uiModel, FieldItemCallback callback) {
        FieldUiModel.Callback itemCallback = new FieldUiModel.Callback() {
            @Override
            public void recyclerViewUiEvents(@NotNull RecyclerViewUiEvents uiEvent) {
                callback.recyclerViewEvent(uiEvent);
            }

            @Override
            public void intent(@NotNull FormIntent intent) {
                callback.intent(intent);
            }

            @Override
            public void mapRequest(@NotNull String coordinateFieldUid, @NotNull String featureType, @Nullable String initialCoordinates) {
                callback.onMapRequest(coordinateFieldUid, FeatureType.valueOfFeatureType(featureType), initialCoordinates);
            }

            @Override
            public void currentLocation(@NotNull String coordinateFieldUid) {
                callback.onCurrentLocationRequest(coordinateFieldUid);
            }

            @Override
            public void onNext() {
                callback.onNext(getLayoutPosition());
            }

            @Override
            public void onItemAction(@NotNull RowAction action) {
                callback.onAction(action);
            }
        };
        uiModel.setCallback(itemCallback);

        binding.setVariable(BR.item, uiModel);
        binding.executePendingBindings();
    }

    public interface FieldItemCallback {
        void intent(@NotNull FormIntent intent);

        void recyclerViewEvent(@NotNull RecyclerViewUiEvents uiEvent);

        void onNext(int layoutPosition);

        void onMapRequest(@NotNull String coordinateFieldUid, @NotNull FeatureType featureType, @Nullable String initialCoordinates);

        void onCurrentLocationRequest(@NotNull String coordinateFieldUid);

        void onAction(RowAction action);
    }
}
