package org.dhis2.data.forms.dataentry.fields.TrackerAssociate;

import android.support.v4.app.FragmentManager;
import android.databinding.ViewDataBinding;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.google.common.eventbus.Subscribe;

import org.dhis2.R;
import org.dhis2.data.forms.dataentry.DataEntryRepository;
import org.dhis2.data.forms.dataentry.fields.FormViewHolder;
import org.dhis2.data.forms.dataentry.fields.RowAction;
import org.dhis2.data.metadata.MetadataRepository;
import org.dhis2.utils.CustomViews.TeiDialog;
import org.hisp.dhis.android.core.trackedentity.TrackedEntityAttributeValueModel;
import org.hisp.dhis.android.core.trackedentity.TrackedEntityInstanceModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static org.dhis2.data.forms.dataentry.DataEntryPresenterImpl.ANM_NAME;
import static org.dhis2.data.forms.dataentry.DataEntryPresenterImpl.HOUSEHOLD_PROGRAM;
import static org.dhis2.data.forms.dataentry.DataEntryPresenterImpl.HOUSE_NO_H;
import static org.dhis2.data.forms.dataentry.DataEntryPresenterImpl.LOCALITYNAME;
import static org.dhis2.data.forms.dataentry.DataEntryPresenterImpl.TYPE_OF_HOUSE_ID;

public class TrackerAssosiateHolder extends FormViewHolder {
    private final TextInputEditText editText;
    private final TextInputLayout inputLayout;
    private final Observable<List<TrackedEntityInstanceModel>> teisObservable;
    private List<TrackedEntityInstanceModel> teis;
    private List<HashMap<String,String>> attribtues;
    private CompositeDisposable compositeDisposable;
    private CompositeDisposable attributeDisposables;
    private TrackedEntityInstanceViewModel model;

    private TeiDialog teiDialog;
    private MetadataRepository metadataRepository;


    TrackerAssosiateHolder(FragmentManager fm, ViewDataBinding binding,
                           FlowableProcessor<RowAction> processor,Observable<List<TrackedEntityInstanceModel>> teis,MetadataRepository metadataRepository){
        super(binding);
        compositeDisposable = new CompositeDisposable();
        attributeDisposables = new CompositeDisposable();
        this.editText = binding.getRoot().findViewById(R.id.input_editText);
        this.inputLayout = binding.getRoot().findViewById(R.id.input_layout);
//        this.description = binding.getRoot().findViewById(R.id.description);
        this.metadataRepository = metadataRepository;

        this.teisObservable = teis;

        attribtues = new ArrayList<HashMap<String, String>>();
        getTeis();
        this.editText.setOnClickListener(view->{
            editText.setEnabled(false);
            teiDialog = new TeiDialog()
                    .setTitle("Test")
                    .setTeis(this.teis,this.attribtues)
                    .setPositiveListener(data ->{
                        processor.onNext(RowAction.create(model.uid(), teiDialog.getSelectedTei()));
                        this.editText.setText(teiDialog.getSelectedTeiName());
                        teiDialog.dismiss();
                        editText.setEnabled(true);
                    })
                    .setNegativeListener(data->{
                        teiDialog.dismiss();
                        editText.setEnabled(true);
                    });
            if(!teiDialog.isAdded()){
                teiDialog.show(fm,model.label());
            }
        });


    }


    @Override
    public void dispose(){compositeDisposable.clear();}

    public void update(TrackedEntityInstanceViewModel viewModel){
        descriptionText = viewModel.description();
        label = new StringBuilder(viewModel.label());
        if(viewModel.mandatory())
            label.append("*");
        this.inputLayout.setHint(label.toString());

//        if(label.length()>16 || viewModel.description() !=null)
//            description.setVisibility(View.VISIBLE);
//        else
//            description.setVisibility(View.GONE);

        if(viewModel.warning()!=null)
            editText.setError(viewModel.warning());
        else if(viewModel.error() != null)
            editText.setError(viewModel.error());
        else
            editText.setError(null);

        if(viewModel.value() != null){
            editText.post(()->editText.setText(viewModel.value()));
        }

        editText.setEnabled(viewModel.editable());

        this.model = viewModel;
    }

    private void getTeis(){
        compositeDisposable.add(teisObservable
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                    trackedEntityInstanceModels ->
                    {
                        this.teis = trackedEntityInstanceModels;
                        if(model.value() !=null){
                            this.inputLayout.setHintAnimationEnabled(false);
                            this.editText.setText(model.value());
                            this.inputLayout.setHintAnimationEnabled(true);
                        }


                        for(TrackedEntityInstanceModel model : trackedEntityInstanceModels){
                            HashMap<String,String> atrs = new HashMap<>();
                            attributeDisposables.add(metadataRepository.getTEIAttributeValues(HOUSEHOLD_PROGRAM,model.uid())
                                    .subscribe(attribtuesmodels -> {
                                        for(TrackedEntityAttributeValueModel value : attribtuesmodels){
                                            switch (value.trackedEntityAttribute()){
                                                case HOUSE_NO_H:
                                                    atrs.put(HOUSE_NO_H,value.value());
                                                    break;

                                                case TYPE_OF_HOUSE_ID:
                                                    atrs.put(TYPE_OF_HOUSE_ID,value.value());
                                                    break;

                                                case ANM_NAME:
                                                    atrs.put(ANM_NAME,value.value());
                                                    break;

                                                case LOCALITYNAME:
                                                    atrs.put(LOCALITYNAME,value.value());
                                                    break;
                                            }
                                        }

//                                        if(attribtues.size() == teis.size()) attributeDisposables.clear();
                                    },Timber::e));
                            attribtues.add(atrs);

                        }


                    },
                    Timber::d
            )
        );
    }

}
