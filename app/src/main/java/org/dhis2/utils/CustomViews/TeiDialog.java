package org.dhis2.utils.CustomViews;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import org.dhis2.R;
import org.hisp.dhis.android.core.trackedentity.TrackedEntityInstanceModel;

import org.dhis2.databinding.DialogTeiBinding;
import android.databinding.DataBindingUtil;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import static org.dhis2.data.forms.dataentry.DataEntryPresenterImpl.ANM_NAME;
import static org.dhis2.data.forms.dataentry.DataEntryPresenterImpl.HOUSEHOLD_PROGRAM;
import static org.dhis2.data.forms.dataentry.DataEntryPresenterImpl.HOUSE_NO_H;
import static org.dhis2.data.forms.dataentry.DataEntryPresenterImpl.LOCALITYNAME;
import static org.dhis2.data.forms.dataentry.DataEntryPresenterImpl.TYPE_OF_HOUSE_ID;

public class TeiDialog extends DialogFragment{
    DialogTeiBinding binding;
    static TeiDialog instance;
    private View.OnClickListener positiveListener;
    private View.OnClickListener negativeListener;

    private String title;
    private List<TrackedEntityInstanceModel> myteis;
    private List<HashMap<String, String>> attributes;
    private Context context;

    private int SelectedItem = -1;


    public static TeiDialog newInstance(){
        if(instance ==null){
            instance = new TeiDialog();

        }
        return instance;
    }

    public TeiDialog(){
        instance = null;
        positiveListener = null;
        negativeListener = null;
        title = null;
    }

    public TeiDialog setPositiveListener(View.OnClickListener listener){
        this.positiveListener = listener;
        return this;
    }

    public TeiDialog setNegativeListener(View.OnClickListener listner){
        this.negativeListener = listner;
        return this;
    }

    public TeiDialog setTitle(String title){
        this.title = title;
        return this;
    }

    public TeiDialog setTeis(List<TrackedEntityInstanceModel> teis,List<HashMap<String,String>> attributes){
        this.myteis = teis;
        this.attributes = attributes;
        return this;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstaceState){
        Dialog dialog = super.onCreateDialog(savedInstaceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,Bundle savedInstanceState){
        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_tei,container,false);

        binding.title.setText(title);
        binding.acceptButton.setOnClickListener(positiveListener);
        binding.clearButton.setOnClickListener(negativeListener);
        MyAdapter myAdapter = new MyAdapter(context,attributes);
        binding.teiList.setAdapter(myAdapter);
        return binding.getRoot();
    }

    public String getSelectedTei(){
        return SelectedItem ==-1  ? null : myteis.get(SelectedItem).uid();
    }

    public String getSelectedTeiName(){
        return SelectedItem ==-1  ? null : myteis.get(SelectedItem).uid();
    }

    public TrackedEntityInstanceModel getSelectedTeiModel(){
        return null;
    }


    private class MyAdapter extends BaseAdapter{
        List<HashMap<String,String>> attributes;
        Context context;

        MyAdapter(Context context,List<HashMap<String,String>> attributes){
            this.context = context;
            this.attributes = attributes;
        }

        @Override
        public int getCount() {
            return MyAdapter.this.attributes.size();
        }

        @Override
        public Object getItem(int position) {
            return attributes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView== null){
                convertView = LayoutInflater.from(context).inflate(R.layout.list_tei_row,parent,false);
            }
            if(position%2==1) convertView.setBackgroundColor(Color.LTGRAY);
            else convertView.setBackgroundColor(Color.WHITE);
//            if(position == SelectedItem){
//                convertView.findViewById(R.id.tei_value).setBackgroundColor(Color.GRAY);
//            }

            ((TextView)convertView.findViewById(R.id.atr_1)).setText(attributes.get(position).get(HOUSE_NO_H));
            ((TextView)convertView.findViewById(R.id.atr_2)).setText(attributes.get(position).get(TYPE_OF_HOUSE_ID));
            ((TextView)convertView.findViewById(R.id.atr_3)).setText(attributes.get(position).get(ANM_NAME));
            ((TextView)convertView.findViewById(R.id.atr_4)).setText(attributes.get(position).get(LOCALITYNAME));

//            ((TextView)convertView.findViewById(R.id.tei_value)).setText(((TrackedEntityInstanceModel)getItem(position)).uid());
            View finalConvertView = convertView;
            convertView.setOnClickListener((data)->{
                SelectedItem = position;
                finalConvertView.setBackgroundColor(Color.GRAY);
            });
            return convertView;
        }
    }
}
