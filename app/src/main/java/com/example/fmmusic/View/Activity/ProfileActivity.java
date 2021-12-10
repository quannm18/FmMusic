package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fmmusic.DAO.UserDAO;
import com.example.fmmusic.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class ProfileActivity extends AppCompatActivity {
    private ImageView imgImageUser;
    private TextView tvNameOfUserTop;
    private TextView tvNameOfUserBot;
    private TextView tvEmailOfUser;
    private MaterialButton btnLogout;
    private MaterialButton btnRePassword;
    private Dialog dialog ;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imgImageUser = (ImageView) findViewById(R.id.imgImageUser);
        tvNameOfUserTop = (TextView) findViewById(R.id.tvNameOfUserTop);
        tvNameOfUserBot = (TextView) findViewById(R.id.tvNameOfUserBot);
        tvEmailOfUser = (TextView) findViewById(R.id.tvEmailOfUser);
        btnLogout = (MaterialButton) findViewById(R.id.btnLogout);
        btnRePassword = (MaterialButton) findViewById(R.id.btnRePassword);
        userDAO = new UserDAO(ProfileActivity.this);

        SharedPreferences sdf = getSharedPreferences("USER_CURRENT",MODE_PRIVATE);
        tvNameOfUserTop.setText("Xin chào "+sdf.getString("USERNAME",""));

        for (int i = 0; i < userDAO.getAllUser().size(); i++) {
            if (userDAO.getAllUser().get(i).getUserName().equals(sdf.getString("USERNAME",""))){
                tvNameOfUserBot.setText(userDAO.getAllUser().get(i).getFullName());
            }
        }
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        btnRePassword.setOnClickListener(new View.OnClickListener() {

            private TextInputLayout tilPassword;
            private TextView textView11;
            private AppCompatButton btnCancelLogin;
            private AppCompatButton btnYesLogIn;


            @Override
            public void onClick(View v) {
                dialog = new Dialog(ProfileActivity.this);
                dialog.setContentView(R.layout.change_pass_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                tilPassword = (TextInputLayout) dialog.findViewById(R.id.tilPassword);
                textView11 = (TextView) dialog.findViewById(R.id.textView11);
                btnCancelLogin = (AppCompatButton) dialog.findViewById(R.id.btnCancelLogin);
                btnYesLogIn = (AppCompatButton) dialog.findViewById(R.id.btnYesLogIn);

                btnYesLogIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sdf = getSharedPreferences("USER_CURRENT", MODE_PRIVATE);
                        String confirmPassword = sdf.getString("GETPASSWORD","");
                        String writePassword = tilPassword.getEditText().getText().toString();
                        for (int i = 0; i < userDAO.getAllUser().size(); i++) {
                            if (writePassword.trim().equals(confirmPassword)){
                                dialog.dismiss();
                                Intent intent = new Intent(ProfileActivity.this,PassChangingActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(ProfileActivity.this, "Sai mật khẩu bạn cần nhập chính xác!!!", Toast.LENGTH_SHORT).show();
                            }
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
            }
        });

    }
}