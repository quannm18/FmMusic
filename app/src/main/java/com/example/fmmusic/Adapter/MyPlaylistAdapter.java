package com.example.fmmusic.Adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fmmusic.Model.PLL;
import com.example.fmmusic.R;

import java.util.List;

public class MyPlaylistAdapter extends RecyclerView.Adapter<MyPlaylistAdapter.MyPlaylistViewHolder> {
    List<PLL> pllList;

    public MyPlaylistAdapter(List<PLL> pllList) {
        this.pllList = pllList;
    }

    @NonNull
    @Override
    public MyPlaylistAdapter.MyPlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_myplaylist,parent,false);
        return new MyPlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPlaylistAdapter.MyPlaylistViewHolder holder, int position) {
        final PLL pll = pllList.get(position);
        holder.tvNamePlaylist.setText(pll.getNamePll());
        if (position==0){
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.button_add_playlist).
                    into(holder.imgThumbnailPlaylist);
            holder.imgThumbnailPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return pllList.size();
    }

    public class MyPlaylistViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView4;
        private ImageView imgThumbnailPlaylist;
        private TextView tvNamePlaylist;

        public MyPlaylistViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView4 = (CardView) itemView.findViewById(R.id.cardView4);
            imgThumbnailPlaylist = (ImageView) itemView.findViewById(R.id.imgThumbnailPlaylist);
            tvNamePlaylist = (TextView) itemView.findViewById(R.id.tvNamePlaylist);

        }
    }
}
