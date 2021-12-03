package com.example.fmmusic.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fmmusic.DAO.PLLDAO;
import com.example.fmmusic.Model.PLL;
import com.example.fmmusic.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MyPlaylistAdapter extends RecyclerView.Adapter<MyPlaylistAdapter.MyPlaylistViewHolder> {
    List<PLL> pllList;
    private Dialog dialog;


    public MyPlaylistAdapter(List<PLL> pllList) {
        this.pllList = pllList;
    }

    @NonNull
    @Override
    public MyPlaylistAdapter.MyPlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_myplaylist, parent, false);
        return new MyPlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPlaylistAdapter.MyPlaylistViewHolder holder, int position) {
        final PLL pll = pllList.get(position);
        holder.tvNamePlaylist.setText(pll.getNamePll());
        if (position == 0) {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.button_add_playlist).
                    into(holder.imgThumbnailPlaylist);
            holder.imgThumbnailPlaylist.setOnClickListener(new View.OnClickListener() {
                private TextView textView11;
                private TextInputLayout tilNamePlaylist;
                private AppCompatButton btnAddPlaylist;

                @Override
                public void onClick(View v) {
                    dialog = new Dialog(v.getContext());
                    dialog.setContentView(R.layout.add_playlist_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    SharedPreferences sdf = v.getContext().getSharedPreferences("USER_CURRENT",MODE_PRIVATE);


                    textView11 = (TextView) dialog.findViewById(R.id.textView11);
                    tilNamePlaylist = (TextInputLayout) dialog.findViewById(R.id.tilNamePlaylist);
                    btnAddPlaylist = dialog.findViewById(R.id.btnAddPlaylist);
                    btnAddPlaylist.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String namePlaylist = tilNamePlaylist.getEditText().getText().toString();
                            PLL pll1 = new PLL();
                            pll1.setNamePll(namePlaylist);
                            pll1.setIdUser( sdf.getString("USERNAME",""));
                            PLLDAO plldao = new PLLDAO(v.getContext());
                            long checking = plldao.insertPLL(pll1);
                            if (checking > 0) {
                                Toast.makeText(v.getContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                pllList.clear();
                                pllList.addAll(plldao.getAllPll());
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(v.getContext(), "Them thất bại", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    dialog.show();
                }
            });
        } else {
            holder.imgThumbnailPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Danh sách bài hát", Toast.LENGTH_LONG).show();
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
