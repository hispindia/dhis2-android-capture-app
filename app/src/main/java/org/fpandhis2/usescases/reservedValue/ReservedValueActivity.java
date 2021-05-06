package org.fpandhis2.usescases.reservedValue;

import android.os.Bundle;

import org.fpandhis2.App;
import org.fpandhis2.BR;
import org.fpandhis2.R;
import org.fpandhis2.databinding.ActivityReservedValueBinding;
import org.fpandhis2.usescases.general.ActivityGlobalAbstract;
import org.fpandhis2.utils.Constants;
import org.fpandhis2.utils.customviews.CustomDialog;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;

public class ReservedValueActivity extends ActivityGlobalAbstract implements ReservedValueView {

    private ActivityReservedValueBinding reservedBinding;
    private ReservedValueAdapter adapter;
    @Inject
    ReservedValuePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) getApplicationContext()).userComponent().plus(new ReservedValueModule(this)).inject(this);

        reservedBinding = DataBindingUtil.setContentView(this, R.layout.activity_reserved_value);
        reservedBinding.setVariable(BR.presenter, presenter);
        adapter = new ReservedValueAdapter();
    }

    @Override
    public void setReservedValues(@NotNull List<ReservedValueModel> reservedValueModels) {
        if (reservedBinding.recycler.getAdapter() == null) {
            reservedBinding.recycler.setAdapter(adapter);
        }
        adapter.submitList(reservedValueModels);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onBackClick() {
        super.onBackPressed();
    }

    @Override
    public void showReservedValuesError() {
        runOnUiThread(() -> new CustomDialog(
                getAbstracContext(),
                getString(R.string.error),
                getString(R.string.no_reserved_values),
                getString(R.string.action_accept),
                null,
                Constants.DESCRIPTION_DIALOG,
                null
        ).show());
    }
}
