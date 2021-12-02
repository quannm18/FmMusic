package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fmmusic.DAO.UserDAO;
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
    private CheckBox cbxRemember;
    private int count = 0;
    private Dialog dialog;
    private ViewGroup viewGroup;
    private UserDAO userdao;
    private MaterialButton btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewGroup = findViewById(R.id.containerLogIn);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);
        tilUserLogIn = (TextInputLayout) findViewById(R.id.tilUserLogIn);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSkipLogIn = findViewById(R.id.btnSkipLogIn);
        tvLostPass = (TextView) findViewById(R.id.tvLostPass);
        tvXinChao = (TextView) findViewById(R.id.tvXinChao);
        tvDesText = (TextView) findViewById(R.id.tv_DesText);
        tvQuotes = (TextView) findViewById(R.id.tvQuotes);
        cbxRemember = findViewById(R.id.cbxRemember);
        btnRegister = (MaterialButton) findViewById(R.id.btnRegister);


        tvQuotes.setVisibility(View.INVISIBLE);
        tilUserLogIn.setVisibility(View.GONE);
        tilPassword.setVisibility(View.GONE);
        btnSignIn.setVisibility(View.GONE);
        tvLostPass.setVisibility(View.GONE);
        tvXinChao.setVisibility(View.GONE);
        tvDesText.setVisibility(View.GONE);
        btnSkipLogIn.setVisibility(View.GONE);
        cbxRemember.setVisibility(View.GONE);
        btnRegister.setVisibility(View.GONE);


        SharedPreferences sdf = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        tilUserLogIn.getEditText().setText(sdf.getString("USERNAME", ""));
        tilPassword.getEditText().setText(sdf.getString("PASSWORD", ""));

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                count++;
                                TransitionManager.beginDelayedTransition(viewGroup);
                                if (count == 5) {
                                    tvQuotes.setVisibility(View.INVISIBLE);
                                } else if (count == 6) {
                                    tvQuotes.setVisibility(View.VISIBLE);
                                    tvQuotes.setText("Cùng khám phá bảng xếp hạng các bản nhạc được yêu thích");
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
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
                cbxRemember.setVisibility(View.VISIBLE);
                btnRegister.setVisibility(View.VISIBLE);
            }
        }.start();
        btnSkipLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSkipSignup();
            }
        });

    }

    private TextView textView10;
    private TextView textView11;
    private AppCompatButton btnCancelLogin;
    private AppCompatButton btnYesLogIn;

    public void openSkipSignup() {
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
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void checkLogin() {
//        userdao = new UserDAO(LoginActivity.this);
//        String username = tilUserLogIn.getEditText().getText().toString();
//        String pass = tilPassword.getEditText().getText().toString();
//        boolean checked = checkBox.isChecked();
//        if (username.trim().isEmpty()) {
//            tilUserLogIn.setError("Vui lòng nhập Tài khoản người dùng");
//            return;
//        } else {
//            tilUserLogIn.setErrorEnabled(false);
//            if (pass.trim().isEmpty()) {
//                tilPassword.setError("Vui lòng nhập mật khẩu người dùng");
//                return;
//            } else {
//                tilPassword.setErrorEnabled(false);
//            }
//            if (userdao.checkLogin(username,pass) > 0 || (username.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("admin"))) {
//                SharedPreferences sdf = getSharedPreferences("USER_FILE",MODE_PRIVATE);
//                SharedPreferences.Editor editor = sdf.edit();
//                editor.putString("PASSWORD",pass);
//                editor.commit();
//                luuThongTinDangNhap(username,pass,checked);
//                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                intent.putExtra("user", username);
//                startActivity(intent);
//                finish();
//            } else {
//                Toast.makeText(getApplicationContext(), "Mật khẩu và tài khoản của bạn Sai vui lòng nhập ", Toast.LENGTH_SHORT).show();
//            }
//        }
        userdao = new UserDAO(LoginActivity.this);
        String userName = tilUserLogIn.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();
        boolean checked = cbxRemember.isChecked();
        if (userName.trim().isEmpty()) {
            tilUserLogIn.setError("Vui lòng nhập Tài khoản người dùng");
            return;
        } else {
            tilUserLogIn.setErrorEnabled(false);
            if (password.trim().isEmpty()) {
                tilPassword.setError("Vui lòng nhập mật khẩu người dùng");
                return;
            } else {
                tilPassword.setErrorEnabled(false);
            }
            if (userdao.checkLogin(userName,password)>0 ||(userName.equalsIgnoreCase("admin")&&password.equalsIgnoreCase("admin"))){
                SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("PASSWORD",password);
                editor.commit();
                luuThongTinDangNhap(userName,password,checked);
                //
                SharedPreferences sdf = getSharedPreferences("USER_CURRENT",MODE_PRIVATE);
                SharedPreferences.Editor setUser =sdf.edit();
                setUser.clear();
                setUser.putString("USERNAME",userName);
                setUser.putString("GETPASSWORD",password);
                setUser.commit();
                //
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("user", userName);
                startActivity(intent);
                finish();
            }

        }

    }


    public void luuThongTinDangNhap(String taiKhoan, String matkhau, boolean remember) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (remember == false) {
            editor.clear();
        } else {
            editor.putString("USERNAME", taiKhoan);
            editor.putString("PASSWORD", matkhau);
            editor.putBoolean("REMEMBER", remember);
        }
        editor.commit();
    }
}