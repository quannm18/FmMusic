package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fmmusic.R;

public class PassChangingActivity extends AppCompatActivity {
//Nguyễn Hoài Nam đảm nhận thằng này
    TextView tvFocusedPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_changing);
        tvFocusedPass = findViewById(R.id.tvFocusedPass);
        tvFocusedPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Focused Password",Toast.LENGTH_SHORT).show();
            }
        });
    }
}