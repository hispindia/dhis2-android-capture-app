package org.dhis2hiv.usescases.reservedValue;

import android.os.Bundle;

import org.dhis2hiv.App;
import org.dhis2hiv.BR;
import org.dhis2hiv.R;
import org.dhis2hiv.databinding.ActivityReservedValueBinding;
import org.dhis2hiv.usescases.general.ActivityGlobalAbstract;
import org.dhis2hiv.utils.Constants;
import org.dhis2hiv.utils.customviews.CustomDialog;
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
