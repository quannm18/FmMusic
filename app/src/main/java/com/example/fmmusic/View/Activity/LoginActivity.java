package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fmmusic.MainActivity;
import com.example.fmmusic.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilPassword;
    private TextInputLayout tilUserLogIn;
    private MaterialButton btnSignIn;
    private TextView tvDangKy;
    private TextView tvTextNull;
    private MaterialButton btnSkipLogIn;
    private TextView tvLostPass;
    private TextView tvXinChao;
    private TextView tvDesText;
    private TextView tvQuotes;
    private int count = 0;
    private Dialog dialog;
    private ViewGroup viewGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewGroup = findViewById(R.id.containerLogIn);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);
        tilUserLogIn = (TextInputLayout) findViewById(R.id.tilUserLogIn);
        btnSignIn =  findViewById(R.id.btnSignIn);
        tvDangKy = (TextView) findViewById(R.id.tvDangKy);
        tvTextNull = (TextView) findViewById(R.id.tvTextNull);
        btnSkipLogIn =  findViewById(R.id.btnSkipLogIn);
        tvLostPass = (TextView) findViewById(R.id.tvLostPass);
        tvXinChao = (TextView) findViewById(R.id.tvXinChao);
        tvDesText = (TextView) findViewById(R.id.tv_DesText);
        tvQuotes = (TextView) findViewById(R.id.tvQuotes);

        tvQuotes.setVisibility(View.INVISIBLE);
        tilUserLogIn.setVisibility(View.GONE);
        tilPassword.setVisibility(View.GONE);
        btnSignIn.setVisibility(View.GONE);
        tvLostPass.setVisibility(View.GONE);
        tvXinChao.setVisibility(View.GONE);
        tvDesText.setVisibility(View.GONE);
        btnSkipLogIn.setVisibility(View.GONE);
        tvDangKy.setVisibility(View.GONE);
        tvTextNull.setVisibility(View.GONE);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = tilUserLogIn.getEditText().getText().toString();
                String pass = tilPassword.getEditText().getText().toString();
                if (username.trim().isEmpty()) {
                    tilUserLogIn.setError("Vui lòng nhập Tài khoản người dùng");
                } else {
                    tilUserLogIn.setErrorEnabled(false);
                }
                if (pass.trim().isEmpty()) {
                    tilPassword.setError("Vui lòng nhập mật khẩu");
                } else {
                    tilPassword.setErrorEnabled(false);
                }
                if (username.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("admin")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                while (!isInterrupted()){
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                count++;
                                TransitionManager.beginDelayedTransition(viewGroup);
                                if (count == 5){
                                    tvQuotes.setVisibility(View.INVISIBLE);
                                } else if (count == 6) {
                                    tvQuotes.setVisibility(View.VISIBLE);
                                    tvQuotes.setText("Cùng khám phá bảng xếp hạng các bản nhạc \n được yêu thích");
                                } else if (count == 11) {
                                    tvQuotes.setVisibility(View.INVISIBLE);
                                } else if (count == 12) {
                                    tvQuotes.setVisibility(View.VISIBLE);
                                    tvQuotes.setText("Hãy đăng nhập để trải nghiệm tối đa cùng \n \"MUSICNAME\"");
                                } else if (count == 17) {
                                    tvQuotes.setVisibility(View.INVISIBLE);
                                } else if (count == 18) {
                                    tvQuotes.setVisibility(View.VISIBLE);
                                    tvQuotes.setText("Nghe nhạc bất cứ đâu bất cứ nới nào");
                                } else if (count == 23) {
                                    tvQuotes.setVisibility(View.INVISIBLE);
                                } else if (count == 24) {
                                    tvQuotes.setVisibility(View.VISIBLE);
                                    tvQuotes.setText("Sở hữu các bài hát Hot nhất hiện nay");
                                    count = 0;
                                    run();
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
         thread.start();
        CountDownTimer countDownTimer = new CountDownTimer(1000, 100) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                TransitionManager.beginDelayedTransition(viewGroup);
                tilUserLogIn.setVisibility(View.VISIBLE);
                tilPassword.setVisibility(View.VISIBLE);
                btnSignIn.setVisibility(View.VISIBLE);
                tvLostPass.setVisibility(View.VISIBLE);
                tvXinChao.setVisibility(View.VISIBLE);
                tvDesText.setVisibility(View.VISIBLE);
                tvQuotes.setVisibility(View.VISIBLE);
                btnSkipLogIn.setVisibility(View.VISIBLE);
                tvDangKy.setVisibility(View.VISIBLE);
                tvTextNull.setVisibility(View.VISIBLE);
            }
        }.start();
        btnSkipLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSkipSigup();
            }
        });

    }

    private TextView textView10;
    private TextView textView11;
    private AppCompatButton btnCancelLogin;
    private AppCompatButton btnYesLogIn;

    public void openSkipSigup() {
        dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.skiplogin_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        textView10 = (TextView) dialog.findViewById(R.id.textView10);
        textView11 = (TextView) dialog.findViewById(R.id.textView11);
        btnCancelLogin = (AppCompatButton) dialog.findViewById(R.id.btnCancelLogin);
        btnYesLogIn = (AppCompatButton) dialog.findViewById(R.id.btnYesLogIn);

        btnCancelLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnYesLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
    }
}