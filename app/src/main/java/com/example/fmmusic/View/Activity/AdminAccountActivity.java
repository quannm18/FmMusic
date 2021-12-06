package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.fmmusic.Adapter.AccountDetailAdapter;
import com.example.fmmusic.DAO.UserDAO;
import com.example.fmmusic.Model.Users;
import com.example.fmmusic.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class AdminAccountActivity extends AppCompatActivity {
    private RecyclerView rcvAllAccount;
    private MaterialButton btnSkipToHome;
    private AccountDetailAdapter accountDetailAdapter;
    private List<Users> usersList;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account);
        rcvAllAccount = (RecyclerView) findViewById(R.id.rcvAllAccount);
        btnSkipToHome = (MaterialButton) findViewById(R.id.btnSkipToHome);

        userDAO = new UserDAO(getApplicationContext());
        usersList = new ArrayList<>();
        usersList = userDAO.getAllUser();
        accountDetailAdapter = new AccountDetailAdapter(usersList);
        rcvAllAccount.setAdapter(accountDetailAdapter);
        rcvAllAccount.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        btnSkipToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}