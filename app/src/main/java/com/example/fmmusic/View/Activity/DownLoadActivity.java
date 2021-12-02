package com.example.fmmusic.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.fmmusic.R;

public class DownLoadActivity extends AppCompatActivity {
    private WebView webViewDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        webViewDownload = (WebView) findViewById(R.id.webViewDownload);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        webViewDownload.loadUrl("http://api.mp3.zing.vn/api/streaming/audio/"+id+"/320");
        finish();
    }
}