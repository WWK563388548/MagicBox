package com.example.wwk.functiondemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.wwk.functiondemo.R;
import com.example.wwk.functiondemo.utils.L;

/**
 * Created by wwk on 17/6/2.
 *
 * Page of news
 */

public class WebViewActivity extends BaseActivity {

    private WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        initializeView();
    }

    private void initializeView() {

        mWebView = (WebView) findViewById(R.id.web_view);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");
        L.information("url: " + url);

        // Set title of news
        getSupportActionBar().setTitle(title);

        // Support JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        // Support zoom
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setWebViewClient(new WebViewClient());
        // Load web page
        mWebView.loadUrl(url);


    }
}
