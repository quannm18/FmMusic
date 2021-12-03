package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fmmusic.DAO.UserDAO;
import com.example.fmmusic.Database.FMMusicDatabase;
import com.example.fmmusic.Model.Users;
import com.example.fmmusic.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class PassChangingActivity extends AppCompatActivity {
    private TextInputLayout tilUserName;
    private TextInputLayout tilOldPass;
    private TextInputLayout tilNewPass;
    private TextInputLayout tilRePass;
    private MaterialButton btnChangePass;
    private UserDAO userDAO;
    private List<Users> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_changing);
        tilUserName = (TextInputLayout) findViewById(R.id.tilUserName);
        tilOldPass = (TextInputLayout) findViewById(R.id.tilOldPass);
        tilNewPass = (TextInputLayout) findViewById(R.id.tilNewPass);
        tilRePass = (TextInputLayout) findViewById(R.id.tilRePass);
        btnChangePass = (MaterialButton) findViewById(R.id.btnChangePass);

        String newPass = tilNewPass.getEditText().getText().toString();
        userDAO = new UserDAO(getApplicationContext());
        userList = userDAO.getAllUser();

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sdf = getSharedPreferences("USER_FILE", MODE_PRIVATE);
                String username = sdf.getString("USERNAME","");
                if (validate()>0){
                    Users users = userDAO.getUser(username);
                    users.setPassWord(tilNewPass.getEditText().getText().toString());
                    userDAO.updatetUser(users);
                    if (userDAO.updatetUser(users)>0) {
                        Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
    public int validate(){
        int check = 1;
        String oldPass = tilOldPass.getEditText().getText().toString();
        String newPass = tilNewPass.getEditText().getText().toString();
        String rePass = tilRePass.getEditText().getText().toString();
        if (oldPass.length() == 0){
            tilOldPass.setErrorEnabled(true);
            tilOldPass.setError("Không được để trống");
            tilNewPass.setErrorEnabled(false);
            tilRePass.setErrorEnabled(false);
        }
        else if(newPass.length() == 0){
            tilNewPass.setErrorEnabled(true);
            tilNewPass.setError("Không được để trống");
            tilOldPass.setErrorEnabled(false);
            tilRePass.setErrorEnabled(false);
        }
        else if(rePass.length() ==0){
            tilRePass.setErrorEnabled(true);
            tilRePass.setError("Không được để trống");
            tilNewPass.setErrorEnabled(false);
            tilOldPass.setErrorEnabled(false);
        }
        else {
            SharedPreferences sdf = getSharedPreferences("USER_FILE", MODE_PRIVATE);
            String oldpassword = sdf.getString("PASSWORD","");
            String password = tilNewPass.getEditText().getText().toString();
            String rePassword = tilRePass.getEditText().getText().toString();
            if (!oldpassword.equals(tilOldPass.getEditText().getText().toString())){
                Toast.makeText(getApplicationContext(), "Sai mat khau cu", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!password.equals(rePassword)){
                Toast.makeText(getApplicationContext(), "Mat khau moi khong giong nhau", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}