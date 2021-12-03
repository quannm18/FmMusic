package com.example.fmmusic.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fmmusic.Model.SingerModel.Singer;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.Persional.SingersLibActivity;

import java.util.List;

public class SingerFavoriteAdapter extends RecyclerView.Adapter<SingerFavoriteAdapter.SingerFavoriteHolder> {
    private List<Singer> singerList;

    public SingerFavoriteAdapter(List<Singer> singerList) {
        this.singerList = singerList;
    }

    @NonNull
    @Override
    public SingerFavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_singer_fvr,parent,false);
        return new SingerFavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingerFavoriteHolder holder, int position) {
        final Singer singer = singerList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(singer.getId())
                .centerCrop()
                .into(holder.imgSingerHomeRow);
        holder.tvSingerHomeRow.setText(singer.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),SingersLibActivity.class);
                intent.putExtra("singer_name_home",singer.getName());
                intent.putExtra("singer_img_home",singer.getId());
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return singerList.size();
    }

    public class SingerFavoriteHolder extends RecyclerView.ViewHolder {
        private ImageView imgSingerHomeRow;
        private TextView tvSingerHomeRow;

        public SingerFavoriteHolder(@NonNull View itemView) {
            super(itemView);
            imgSingerHomeRow = (ImageView) itemView.findViewById(R.id.imgSingerHomeRow);
            tvSingerHomeRow = (TextView) itemView.findViewById(R.id.tvSingerHomeRow);

        }
    }
}
