package org.dhis2.data.forms.dataentry.fields.orgUnit;

import android.databinding.ViewDataBinding;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.dhis2.R;
import org.dhis2.data.forms.dataentry.fields.FormViewHolder;
import org.dhis2.data.forms.dataentry.fields.RowAction;
import org.dhis2.utils.CustomViews.OrgUnitDialog;
import org.dhis2.utils.StringUtils;
import org.hisp.dhis.android.core.organisationunit.OrganisationUnitModel;
import org.hisp.dhis.android.core.utils.Utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * QUADRAM. Created by ppajuelo on 19/03/2018.
 */

public class OrgUnitHolder extends FormViewHolder {
    private final TextInputEditText editText;
    private final TextInputLayout inputLayout;
    private final Observable<List<OrganisationUnitModel>> orgUnitsObservable;
    private final ImageView description;
    private List<OrganisationUnitModel> orgUnits;
    private OrgUnitDialog orgUnitDialog;
    private CompositeDisposable compositeDisposable;
    private OrgUnitViewModel model;
  /*  @NonNull
    private BehaviorProcessor<OrgUnitViewModel> model;*/

    OrgUnitHolder(FragmentManager fm, ViewDataBinding binding, FlowableProcessor<RowAction> processor, Observable<List<OrganisationUnitModel>> orgUnits) {
        super(binding);
        compositeDisposable = new CompositeDisposable();
        this.editText = binding.getRoot().findViewById(R.id.input_editText);
        this.inputLayout = binding.getRoot().findViewById(R.id.input_layout);
        this.description = binding.getRoot().findViewById(R.id.descriptionLabel);
        this.orgUnitsObservable = orgUnits;
        getOrgUnits();
        this.editText.setOnClickListener(view -> {
            editText.setEnabled(false);
            orgUnitDialog = new OrgUnitDialog()
                    .setTitle(model.label())
                    .setMultiSelection(true)
                    .setOrgUnits(this.orgUnits)
                    .setPossitiveListener(data -> {

                        processor.onNext(RowAction.create(model.uid(),
                                Utils.joinCollectionWithSeparator(orgUnitDialog.getSelectedOrgUnits(),";")));
//                        processor.onNext(RowAction.create(model.uid(), orgUnitDialog.getSelectedOrgUnit()));
//                        this.editText.setText(orgUnitDialog.getSelectedOrgUnitName());
                        this.editText.setText(Utils.joinCollectionWithSeparator(orgUnitDialog.getSelectedOrgUnitsNameString(),";"));
                        orgUnitDialog.dismiss();
                        editText.setEnabled(true);
                    })
                    .setNegativeListener(data -> {
                        orgUnitDialog.dismiss();
                        editText.setEnabled(true);
                    });
            if (!orgUnitDialog.isAdded())
                orgUnitDialog.show(fm, model.label());
        });



    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }

    public void update(OrgUnitViewModel viewModel) {

        descriptionText = viewModel.description();
        label = new StringBuilder(viewModel.label());
        if (viewModel.mandatory())
            label.append("*");
        this.inputLayout.setHint(label.toString());

        if (label.length() > 16 || viewModel.description() != null)
            description.setVisibility(View.VISIBLE);
        else
            description.setVisibility(View.GONE);

        if (viewModel.warning() != null)
            editText.setError(viewModel.warning());
        else if (viewModel.error() != null)
            editText.setError(viewModel.error());
        else
            editText.setError(null);

        if (viewModel.value() != null) {
//            editText.post(() -> editText.setText(getOrgUnitName(viewModel.value())));
//            if(viewModel.value().contains(";")){
//
//                String orgunitvalue = "";
//                List<String> orgids = Arrays.asList(viewModel.value().split(";"));
//                for(String  oid : orgids){
//                    orgunitvalue+= getOrgUnitName(oid)+" ";
//                }
//                final String val = orgunitvalue;
//                editText.post(()->editText.setText(val));
//            }else{
//                editText.post(() -> editText.setText(getOrgUnitName(viewModel.value())));
//            }
            String orgunitvalue = "";
                List<String> orgids = Arrays.asList(viewModel.value().split(";"));
                if(orgUnits==null){
                    List<String> orgnames = new ArrayList<>();
                    compositeDisposable.add(orgUnitsObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io())
                            .subscribe(
                                    orgUnitViewModels ->
                                    {
                                        for(String  oid : orgids){
                                            orgnames.add(getOrgUnitName(oid));
                                        }
                                        editText.post(()->editText.setText(Utils.joinCollectionWithSeparator(orgnames," ")));
                                    },
                                    Timber::d
                            )
                    );

                }else{
                    for(String  oid : orgids){
                        orgunitvalue+= getOrgUnitName(oid)+" ";
                    }
                    final String val = orgunitvalue;
                    editText.post(()->editText.setText(val));
                }

//            editText.post(() -> editText.setText(getOrgUnitName(viewModel.value())));

        }
        editText.setEnabled(viewModel.editable());

        this.model = viewModel;

    }

    private String getOrgUnitName(String value) {
        String orgUnitName = null;
        if (orgUnits != null) {
            for (OrganisationUnitModel orgUnit : orgUnits) {
                if (orgUnit.uid().equals(value))
                    orgUnitName = orgUnit.displayName();
            }
        }
        Log.d(" ou ",orgUnitName+"");
        return orgUnitName;
    }

    private void getOrgUnits() {
        compositeDisposable.add(orgUnitsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        orgUnitViewModels ->
                        {
                            this.orgUnits = orgUnitViewModels;
                            if (model.value() != null) {
                                this.inputLayout.setHintAnimationEnabled(false);
                                this.editText.setText(getOrgUnitName(model.value()));
                                this.inputLayout.setHintAnimationEnabled(true);
                            }
                        },
                        Timber::d
                )
        );
    }
}
