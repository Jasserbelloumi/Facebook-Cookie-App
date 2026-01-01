package com.cookie.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText cookieInput = findViewById(R.id.cookieInput);
        Button btnLoad = findViewById(R.id.btnLoad);
        final WebView webView = findViewById(R.id.webView);
        
        webView.getSettings().setJavaScriptEnabled(true);

        btnLoad.setOnClickListener(v -> {
            String cookieString = cookieInput.getText().toString();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            
            // تقسيم الكوكيز وحقنها
            String[] cookies = cookieString.split(";");
            for (String cookie : cookies) {
                cookieManager.setCookie("https://www.facebook.com", cookie);
            }
            
            webView.loadUrl("https://m.facebook.com");
        });
    }
}
