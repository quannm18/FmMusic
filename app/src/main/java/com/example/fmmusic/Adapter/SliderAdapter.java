package com.example.fmmusic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fmmusic.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderHolder> {
    private List<String> list;

    public SliderAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public SliderHolder onCreateViewHolder(ViewGroup parent) {
        return new SliderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_slider,parent,false));
    }

    @Override
    public void onBindViewHolder(SliderHolder viewHolder, int position) {
        final String s = list.get(position);

        Glide.with(viewHolder.itemView.getContext())
                .load(s)
                .centerCrop()
                .into(viewHolder.imgSliderRow);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public class SliderHolder extends SliderViewAdapter.ViewHolder {

        private ImageView imgSliderRow;
        public SliderHolder(@NonNull View itemView) {
            super(itemView);

            imgSliderRow = (ImageView) itemView.findViewById(R.id.imgSliderRow);

        }
    }
}
