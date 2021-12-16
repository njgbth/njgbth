package com.example.njgbth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val webView = findViewById<WebView>(R.id.webView)
        webView.apply {
            webViewClient = WebViewClient()//페이지를 부르는 함수
            webChromeClient = WebChromeClient()//크롬 기본설정. 알림시 필요
            settings.javaScriptEnabled = true//자바스크립트 허용
        }

        webView.loadUrl("https://www.naver.co.kr/")//여기에 주소 URL변경하기==< 레시피 주소
    }

    override fun onBackPressed() {
        val webView = findViewById<WebView>(R.id.webView)
        if (webView.canGoBack()) {//뒤로가기시 뒤가 있으면 페이지 뒤로가기, 없으면 앱뒤로가기
            webView.goBack()
        }
        else {
            finish()
        }


    }
}
