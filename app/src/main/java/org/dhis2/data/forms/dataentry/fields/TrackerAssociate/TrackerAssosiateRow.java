package org.dhis2.data.forms.dataentry.fields.TrackerAssociate;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.dhis2.R;
import org.dhis2.BR;
import org.dhis2.data.forms.dataentry.fields.Row;
import org.dhis2.data.forms.dataentry.fields.RowAction;
import org.dhis2.data.metadata.MetadataRepository;
import org.dhis2.databinding.FormButtonBinding;

import org.hisp.dhis.android.core.trackedentity.TrackedEntityInstanceModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.processors.FlowableProcessor;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

public class TrackerAssosiateRow implements Row<TrackerAssosiateHolder,TrackedEntityInstanceViewModel>
{

    private final boolean isBgTransparent;
    private final FlowableProcessor<RowAction> processor;
    private final LayoutInflater inflater;
    private final FragmentManager fm;
    private final Observable<List<TrackedEntityInstanceModel>> teis;
    private final String renderType;
    private FormButtonBinding binding;
    private MetadataRepository metadataRepository;

    public TrackerAssosiateRow(FragmentManager fm,LayoutInflater inflater,FlowableProcessor<RowAction> processor,
                               boolean isBgTransparent,Observable<List<TrackedEntityInstanceModel>> teis){
        this.inflater = inflater;
        this.processor = processor;
        this.isBgTransparent = isBgTransparent;
        this.fm = fm;
        this.renderType = null;
        this.teis = teis;

    }

    public TrackerAssosiateRow(FragmentManager fm, LayoutInflater layoutInflater, FlowableProcessor<RowAction> processor,
                               @NonNull FlowableProcessor<Integer> currentPosition,
                               boolean isBgTransparent,
                               String renderType,
                               Observable<List<TrackedEntityInstanceModel>> teis, MetadataRepository metadataRepository){
        this.inflater = layoutInflater;
        this.processor = processor;
        this.isBgTransparent = isBgTransparent;
        this.fm = fm;
        this.renderType = renderType;
        this.teis = teis;
        this.metadataRepository = metadataRepository;
    }

    @NonNull
    @Override
    public TrackerAssosiateHolder onCreate(@NonNull ViewGroup parent){
        ViewDataBinding binding = DataBindingUtil.inflate(
                inflater,R.layout.custom_text_view,parent,false
        );
        binding.setVariable(BR.renderType,renderType);
        binding.executePendingBindings();

        binding.getRoot().findViewById(R.id.input_editText).setFocusable(false);
        binding.getRoot().findViewById(R.id.input_editText).setClickable(true);
        return new TrackerAssosiateHolder(fm,binding,processor,teis,metadataRepository);
    }

    @Override
    public void onBind(@NonNull TrackerAssosiateHolder viewHolder,@NonNull TrackedEntityInstanceViewModel viewModel){
        viewHolder.update(viewModel);
    }

    @Override
    public void deAttach(@NonNull TrackerAssosiateHolder viewHolder){
        viewHolder.dispose();
    }
}
