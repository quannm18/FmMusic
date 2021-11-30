package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fmmusic.R;
import com.google.android.material.button.MaterialButton;

public class ProfileActivity extends AppCompatActivity {
    private ImageView imgImageUser;
    private TextView tvNameOfUserTop;
    private TextView tvNameOfUserBot;
    private TextView tvEmailOfUser;
    private MaterialButton btnLogout;
    private MaterialButton btnRePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //test
        imgImageUser = (ImageView) findViewById(R.id.imgImageUser);
        tvNameOfUserTop = (TextView) findViewById(R.id.tvNameOfUserTop);
        tvNameOfUserBot = (TextView) findViewById(R.id.tvNameOfUserBot);
        tvEmailOfUser = (TextView) findViewById(R.id.tvEmailOfUser);
        btnLogout = (MaterialButton) findViewById(R.id.btnLogout);
        btnRePassword = (MaterialButton) findViewById(R.id.btnRePassword);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnRePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,PassChangingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}