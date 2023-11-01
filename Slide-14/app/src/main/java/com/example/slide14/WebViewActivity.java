package com.example.slide14;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    WebView webViewInNewActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webViewInNewActivity = findViewById(R.id.webViewInNewActivity);
        webViewInNewActivity.getSettings().setJavaScriptEnabled(true);
        webViewInNewActivity.setWebViewClient(new WebViewClient());

        // Get the URL from the input in the main activity
        String url = getIntent().getStringExtra("url");

        if (url != null) {
            webViewInNewActivity.loadUrl(url);
        }
    }
}
