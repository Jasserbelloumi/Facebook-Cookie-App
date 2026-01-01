package com.cookie.app;

import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    EditText cookieInput;
    Button btnInject;

    @Override
    protected void Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        cookieInput = findViewById(R.id.cookieInput);
        btnInject = findViewById(R.id.btnInject);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        btnInject.setOnClickListener(v -> {
            String fullCookie = cookieInput.getText().toString();
            if (!fullCookie.isEmpty()) {
                injectCookie(fullCookie);
            } else {
                Toast.makeText(this, "الرجاء وضع الكوكيز أولاً!", Toast.LENGTH_SHORT).show();
            }
        });

        webView.loadUrl("https://m.facebook.com");
    }

    public void injectCookie(String cookieString) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        
        // تقسيم الكوكيز وحقنها
        String[] cookies = cookieString.split(";");
        for (String cookie : cookies) {
            cookieManager.setCookie("https://.facebook.com", cookie);
        }
        
        Toast.makeText(this, "تم الحقن! جاري التحديث...", Toast.LENGTH_SHORT).show();
        webView.reload();
    }
}
