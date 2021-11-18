package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.fmmusic.R;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {
    private AppCompatButton button;
    private TextInputLayout tilUserName;
    private TextInputLayout tilFullName;
    private TextInputLayout tilUserPass;
    private TextInputLayout tilRePass;
    private ViewGroup viewGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        viewGroup = findViewById(R.id.containerSignUp);
        button = (AppCompatButton) findViewById(R.id.button);
        tilUserName = (TextInputLayout) findViewById(R.id.tilUserName);
        tilFullName = (TextInputLayout) findViewById(R.id.tilFullName);
        tilUserPass = (TextInputLayout) findViewById(R.id.tilUserPass);
        tilRePass = (TextInputLayout) findViewById(R.id.tilRePass);

        button.setVisibility(View.GONE);
        tilUserName.setVisibility(View.GONE);
        tilFullName.setVisibility(View.GONE);
        tilUserPass.setVisibility(View.GONE);
        tilRePass.setVisibility(View.GONE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        CountDownTimer countDownTimer = new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                TransitionManager.beginDelayedTransition(viewGroup);
                button.setVisibility(View.VISIBLE);
                tilUserName.setVisibility(View.VISIBLE);
                tilFullName.setVisibility(View.VISIBLE);
                tilUserPass.setVisibility(View.VISIBLE);
                tilRePass.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}