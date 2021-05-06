package org.dhis2afgamis.usescases.datasets.dataSetTable.dataSetSection;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;

import org.dhis2afgamis.databinding.ItemDatasetHeaderBinding;

import androidx.databinding.ObservableField;

/**
 * QUADRAM. Created by ppajuelo on 02/10/2018.
 */

public class DataSetRHeaderHeader extends AbstractViewHolder {

    protected ItemDatasetHeaderBinding binding;

    DataSetRHeaderHeader(ItemDatasetHeaderBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(String rowHeaderTitle, ObservableField<DataSetTableAdapter.TableScale> tableScale) {
        binding.setTableScale(tableScale);
        binding.title.setText(rowHeaderTitle);
        binding.title.setSelected(true);
    }
}
