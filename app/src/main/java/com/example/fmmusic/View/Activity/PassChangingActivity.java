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
                        Toast.makeText(getApplicationContext(), "?????i m???t kh???u th??nh c??ng", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "?????i m???t kh???u th???t b???i", Toast.LENGTH_SHORT).show();
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
        if (oldPass.trim().length()==0&&newPass.trim().length()==0&&rePass.trim().length()==0){
            tilOldPass.setError("Kh??ng ???????c ????? tr???ng");
            tilNewPass.setError("Kh??ng ???????c ????? tr???ng");
            tilRePass.setError("Kh??ng ???????c ????? tr???ng");
            return check = -1;
        }
        tilNewPass.setError("");
        tilOldPass.setError("");
        tilRePass.setError("");

        if (oldPass.trim().length() == 0){
            tilOldPass.setErrorEnabled(true);
            tilOldPass.setError("Kh??ng ???????c ????? tr???ng");
            tilNewPass.setError("");
            tilRePass.setError("");
            return check = -1;
        }
        if(newPass.trim().length() == 0){
            tilNewPass.setErrorEnabled(true);
            tilNewPass.setError("Kh??ng ???????c ????? tr???ng");
            tilOldPass.setError("");
            tilRePass.setError("");
            return check = -1;
        }
        if(rePass.trim().length() ==0){
            tilRePass.setErrorEnabled(true);
            tilRePass.setError("Kh??ng ???????c ????? tr???ng");
            tilNewPass.setError("");
            tilOldPass.setError("");
            return check = -1;
        }
        SharedPreferences sdf = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String oldPassword = sdf.getString("PASSWORD","");
        if (!oldPassword.equals(oldPass)){
            tilOldPass.setError("Sai m???t kh???u c??!");
            return check = -1;
        }
        tilOldPass.setError("");
        if (!newPass.equals(rePass)){
            tilNewPass.setError("M???t kh???u kh??ng tr??ng kh???p!");
            tilRePass.setError("M???t kh???u kh??ng tr??ng kh???p!");
            return check = -1;
        }
        tilNewPass.setError("");
        tilRePass.setError("");
        return check;
    }

    @Override
    public void onBackPressed() {
        finish();
//        super.onBackPressed();
    }
}