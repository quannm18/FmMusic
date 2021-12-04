package com.example.fmmusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fmmusic.Model.PLL;
import com.example.fmmusic.R;

import java.util.List;


public class CustomSpinerAdapter extends ArrayAdapter<PLL> {
    private List<PLL> pllList;
    private TextView tvIDPLL;
    private TextView tvNamePLL;

    public CustomSpinerAdapter(@NonNull Context context, List<PLL> pllList) {
        super(context, 0,pllList);
        this.pllList = pllList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_pll_item,null);
            tvNamePLL = (TextView) view.findViewById(R.id.tvNamePLL);
            tvIDPLL = (TextView) view.findViewById(R.id.tvIDPLL);
        }
        final  PLL item = pllList.get(position);
        if (item != null){

            tvIDPLL.setText(item.getIdPLL()+":");

            tvNamePLL.setText(item.getNamePll());

        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_pll_item,null);
            tvNamePLL = (TextView) view.findViewById(R.id.tvNamePLL);
            tvIDPLL = (TextView) view.findViewById(R.id.tvIDPLL);
        }
        final  PLL item = pllList.get(position);
        if (item != null){

            tvIDPLL.setText(item.getIdPLL()+":");

            tvNamePLL.setText(item.getNamePll());

        }
        return view;
    }

}
