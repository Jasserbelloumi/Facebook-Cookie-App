package com.cookie.app;

import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText cookieInput = findViewById(R.id.cookieInput);
        Button btnLoad = findViewById(R.id.btnLoad);
        final WebView webView = findViewById(R.id.webView);
        
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        btnLoad.setOnClickListener(v -> {
            String cookieString = cookieInput.getText().toString();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            
            // مسح الكوكيز القديمة
            cookieManager.removeAllCookies(null);
            
            // حقن الكوكيز الجديدة
            String[] cookies = cookieString.split(";");
            for (String cookie : cookies) {
                cookieManager.setCookie("https://www.facebook.com", cookie);
            }
            
            cookieManager.flush();
            webView.loadUrl("https://m.facebook.com");
        });
    }
}
