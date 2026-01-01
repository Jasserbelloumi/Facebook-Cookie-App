package com.cookie.app

import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cookieInput = findViewById<EditText>(R.id.cookieInput)
        val btnOpen = findViewById<Button>(R.id.btnOpen)
        val webView = findViewById<WebView>(R.id.webview)

        btnOpen.setOnClickListener {
            val cookieString = cookieInput.text.toString().trim()
            
            if (cookieString.isNotEmpty()) {
                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                cookieManager.setAcceptThirdPartyCookies(webView, true)
                
                // تنظيف الجلسات السابقة
                cookieManager.removeAllCookies(null)
                cookieManager.flush()

                // حقن الكوكيز لـ mbasic و web و facebook
                val domains = arrayOf("https://mbasic.facebook.com", "https://m.facebook.com", "https://www.facebook.com")
                
                val cookies = cookieString.split(";")
                for (domain in domains) {
                    for (cookie in cookies) {
                        if (cookie.contains("=")) {
                            cookieManager.setCookie(domain, cookie.trim())
                        }
                    }
                }
                cookieManager.flush()

                // إعداد المتصفح
                webView.visibility = View.VISIBLE
                btnOpen.visibility = View.GONE
                cookieInput.visibility = View.GONE
                
                webView.settings.javaScriptEnabled = true
                webView.settings.domStorageEnabled = true
                webView.settings.userAgentString = "Mozilla/5.0 (Linux; Android 10; Mi 9T Pro) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"
                
                webView.webViewClient = WebViewClient()
                webView.loadUrl("https://mbasic.facebook.com")
                
                Toast.makeText(this, "تم حقن الجلسة بنجاح", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "يرجى إدخال الكوكيز أولاً", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
