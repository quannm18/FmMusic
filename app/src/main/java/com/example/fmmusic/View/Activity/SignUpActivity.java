package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.fmmusic.DAO.UserDAO;
import com.example.fmmusic.Model.Users;
import com.example.fmmusic.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private AppCompatButton btnSignUp;
    private TextInputLayout tilUserName;
    private TextInputLayout tilFullName;
    private TextInputLayout tilUserPass;
    private TextInputLayout tilRePass;
    private ViewGroup viewGroup;

    private List<Users> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usersList = new ArrayList<>();

        viewGroup = findViewById(R.id.containerSignUp);
        btnSignUp = (AppCompatButton) findViewById(R.id.btnSignUp);
        tilUserName = (TextInputLayout) findViewById(R.id.tilUserName);
        tilFullName = (TextInputLayout) findViewById(R.id.tilFullName);
        tilUserPass = (TextInputLayout) findViewById(R.id.tilUserPass);
        tilRePass = (TextInputLayout) findViewById(R.id.tilRePass);

        btnSignUp.setVisibility(View.GONE);
        tilUserName.setVisibility(View.GONE);
        tilFullName.setVisibility(View.GONE);
        tilUserPass.setVisibility(View.GONE);
        tilRePass.setVisibility(View.GONE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                TransitionManager.beginDelayedTransition(viewGroup);
                btnSignUp.setVisibility(View.VISIBLE);
                tilUserName.setVisibility(View.VISIBLE);
                tilFullName.setVisibility(View.VISIBLE);
                tilUserPass.setVisibility(View.VISIBLE);
                tilRePass.setVisibility(View.VISIBLE);
            }
        }.start();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = tilUserName.getEditText().getText().toString();
                String fullname = tilFullName.getEditText().getText().toString();
                String password = tilUserPass.getEditText().getText().toString();
                String rePassword = tilRePass.getEditText().getText().toString();

                Users users = new Users();
                users.setUserName(username);
                users.setFullName(fullname);
                users.setPassWord(password);
                users.setRePass(rePassword);

                if (password.length() == rePassword.length()){
                    UserDAO userDAO = new UserDAO(SignUpActivity.this);
                    long check = userDAO.insertUser(users);
                    if (check > 0) {
                        Toast.makeText(SignUpActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                        usersList.clear();
                        usersList.addAll(userDAO.getAllUser());
                        Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpActivity.this, "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SignUpActivity.this, "Mat khau khong giong nhau", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}