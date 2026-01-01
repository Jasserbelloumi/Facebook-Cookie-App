package com.cookie.app

import android.os.Bundle
import android.webkit.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // واجهة برمجية بسيطة (زر وحقل نص)
        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(30, 30, 30, 30)
        }

        val cookieInput = EditText(this).apply { hint = "Paste Facebook Cookie Here" }
        val loginButton = Button(this).apply { text = "Login with Cookie" }
        val webView = WebView(this).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webViewClient = WebViewClient()
        }

        layout.addView(cookieInput)
        layout.addView(loginButton)
        layout.addView(webView)
        setContentView(layout)

        loginButton.setOnClickListener {
            val cookieString = cookieInput.text.toString()
            if (cookieString.isNotEmpty()) {
                injectCookie(cookieString)
                webView.loadUrl("https://www.facebook.com")
            } else {
                Toast.makeText(this, "Please paste a cookie!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun injectCookie(cookie: String) {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        val cookies = cookie.split(";")
        for (c in cookies) {
            cookieManager.setCookie("facebook.com", c)
        }
        cookieManager.flush()
    }
}
