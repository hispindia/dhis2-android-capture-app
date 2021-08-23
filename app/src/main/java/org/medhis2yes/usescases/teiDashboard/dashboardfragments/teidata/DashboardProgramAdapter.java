package org.medhis2yes.usescases.teiDashboard.dashboardfragments.teidata;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.medhis2yes.R;
import org.medhis2yes.databinding.ItemDashboardProgramBinding;
import org.medhis2yes.usescases.teiDashboard.DashboardProgramModel;

/**
 * Created by ppajuelo on 27/02/2018.
 *
 */

public class DashboardProgramAdapter extends RecyclerView.Adapter<DashboardProgramViewHolder> {

    private final TEIDataContracts.Presenter presenter;
    private DashboardProgramModel dashboardProgramModel;

    public DashboardProgramAdapter(TEIDataContracts.Presenter presenter, DashboardProgramModel program) {
        this.dashboardProgramModel = program;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public DashboardProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDashboardProgramBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_dashboard_program, parent, false);
        return new DashboardProgramViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardProgramViewHolder holder, int position) {
        holder.bind(presenter, dashboardProgramModel, position);
    }

    @Override
    public int getItemCount() {
        return dashboardProgramModel.getEnrollmentPrograms().size();
    }
}
