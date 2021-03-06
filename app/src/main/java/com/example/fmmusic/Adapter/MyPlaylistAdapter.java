package com.example.fmmusic.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fmmusic.DAO.PLLDAO;
import com.example.fmmusic.DAO.PLLSongDAO;
import com.example.fmmusic.Model.PLL;
import com.example.fmmusic.Model.PLLSong;
import com.example.fmmusic.R;
import com.example.fmmusic.View.Activity.MySongPlaylist_Activity;

import java.util.List;

public class MyPlaylistAdapter extends RecyclerView.Adapter<MyPlaylistAdapter.MyPlaylistViewHolder> {
    List<PLL> pllList;
    private Dialog dialog;
    private PLLSongDAO pllSongDAO;
    private List<PLLSong> pllSongList;
    private AppCompatButton btnYesDelete;

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
        holder.imgThumbnailPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pllSongDAO = new PLLSongDAO(v.getContext());
                pllSongList = pllSongDAO.getSongFromPLL(pll.getIdPLL());
                if (!pllSongList.isEmpty()){
                    Log.e("pllAdapter", ""+pll.getIdPLL() );
                    Intent intent = new Intent(holder.itemView.getContext(), MySongPlaylist_Activity.class);
                    Bundle bundle  = new Bundle();
                    bundle.putInt("MyPlayListAdapter",pll.getIdPLL());
                    bundle.putString("namePLL",""+pll.getNamePll());
                    intent.putExtra("idPLLFromMyPlaylistAdapter",bundle);
                    v.getContext().startActivity(intent);
                }else {
                    dialog = new Dialog(v.getContext());
                    dialog.setContentView(R.layout.playlist_empty_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    SharedPreferences sdf = v.getContext().getSharedPreferences("USER_CURRENT", MODE_PRIVATE);
                    btnYesDelete = (AppCompatButton) dialog.findViewById(R.id.btnYesDelete);

                    btnYesDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }

                    });
                    dialog.show();
                }
            }
        });

        holder.imgThumbnailPlaylist.setOnLongClickListener(new View.OnLongClickListener() {
            private TextView textView10;
            private TextView textView11;
            private AppCompatButton btnCancelLogin;
            private AppCompatButton btnYesLogIn;
            @Override
            public boolean onLongClick(View v) {
                dialog = new Dialog(holder.itemView.getContext());
                dialog.setContentView(R.layout.row_delete_playlist_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                textView10 = (TextView) dialog.findViewById(R.id.textView10);
                textView11 = (TextView) dialog.findViewById(R.id.textView11);
                btnCancelLogin = (AppCompatButton) dialog.findViewById(R.id.btnCancelLogin);
                btnYesLogIn = (AppCompatButton) dialog.findViewById(R.id.btnYesLogIn);

                textView10.setText("B???n c?? ch???c mu???n x??a ?");
                btnYesLogIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        PLLDAO plldao1 = new PLLDAO(holder.itemView.getContext());
                        boolean check = plldao1.deletePLL(pll.getIdPLL());
                        if (check){
                            pllList.remove(holder.getAdapterPosition());
                            notifyItemChanged(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }

                    }
                });
                btnCancelLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return false;
            }
        });

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
