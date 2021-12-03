package com.example.fmmusic.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fmmusic.DAO.UserDAO;
import com.example.fmmusic.Model.Users;
import com.example.fmmusic.R;

import java.util.List;

public class AccountDetailAdapter extends RecyclerView.Adapter<AccountDetailAdapter.MyAccountHolder> {
    private List<Users> usersList;
    private AppCompatButton btnCancelDelete;
    private AppCompatButton btnYesDelete;



    public AccountDetailAdapter(List<Users> usersList) {
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public MyAccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_detail_rows,parent,false);
        return new MyAccountHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAccountHolder holder, int position) {
        final Users users = usersList.get(position);
        holder.tvAccountName.setText("Tên tài khoản: " + users.getUserName());
        holder.tvAccountFullName.setText("Họ tên: " + users.getFullName());
        holder.tvAccountPassword.setText("Mật khẩu: " + users.getPassWord());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.delete_account_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                btnCancelDelete = (AppCompatButton) dialog.findViewById(R.id.btnCancelDelete);
                btnYesDelete = (AppCompatButton) dialog.findViewById(R.id.btnYesDelete);
                btnYesDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserDAO userDAO = new UserDAO(v.getContext());
                        int check = userDAO.DeleteUser(users.getUserName());
                        if(check > 0){
                            usersList.remove(users);
                            notifyItemRemoved(holder.getAdapterPosition());
                            Toast.makeText(v.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });
                btnCancelDelete.setOnClickListener(new View.OnClickListener() {
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
        return usersList.size();
    }

    public class MyAccountHolder extends RecyclerView.ViewHolder {
        private TextView tvAccountName;
        private TextView tvAccountFullName;
        private TextView tvAccountPassword;

        public MyAccountHolder(@NonNull View itemView) {
            super(itemView);
            tvAccountName = (TextView) itemView.findViewById(R.id.tvAccountName);
            tvAccountFullName = (TextView) itemView.findViewById(R.id.tvAccountFullName);
            tvAccountPassword = (TextView) itemView.findViewById(R.id.tvAccountPassword);
        }
    }
}
