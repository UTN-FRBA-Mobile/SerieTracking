package com.example.serietracking.login

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.HttpConstants
import retrofit2.Call
import retrofit2.Response
import android.webkit.WebResourceRequest
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.serietracking.*
import com.example.serietracking.Fragments.UserTVShowsFragment
import com.example.serietracking.account.AccountService
import com.example.serietracking.login.dto.CreateSessionRequest
import com.example.serietracking.login.dto.CreateSessionResponse
import com.example.serietracking.login.dto.RequestTokenResponse
import com.example.serietracking.network.ErrorLoggingCallback


class LoginWebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_web_view)

        Log.d("log","calling create request token")
        ApiClient.apiInterface.createRequestToken(HttpConstants.API_KEY).enqueue(object: ErrorLoggingCallback<RequestTokenResponse>() {
            override fun onResponse(call: Call<RequestTokenResponse>, response: Response<RequestTokenResponse>) {
                Log.d("log","create request token finished")
                response.body().let { requestTokenResponse ->
                    val myWebView: WebView = findViewById(R.id.webview)
                    myWebView.getSettings().setJavaScriptEnabled(true);
                    myWebView.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                            Log.i("log", "Received url for rendering: " + request.url.toString())
                            view.loadUrl(request.url.toString())
                            return false
                        }

                        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                            Log.i("log", "Received url: " + url)
                            if (".*/authenticate/.*/allow".toRegex().matches(url)) {
                                ApiClient.apiInterface.createSession(HttpConstants.API_KEY,
                                    CreateSessionRequest(requestTokenResponse.requestToken)
                                ).enqueue(object: ErrorLoggingCallback<CreateSessionResponse>() {
                                    override fun onResponse(call: Call<CreateSessionResponse>, response: Response<CreateSessionResponse>) {
                                        response.body().let { createSessionResponse ->
                                            if(createSessionResponse!=null){
                                                AccountService.setSessionId(createSessionResponse.sessionId)
                                                val intent = Intent(this@LoginWebViewActivity, MainActivity::class.java)
                                                startActivity(intent)
                                            }

                                        }
                                    }
                                })
                            }
                        }
                    }
                    Log.d("log","loading login")
                    myWebView.loadUrl("https://www.themoviedb.org/authenticate/" + requestTokenResponse.requestToken)
                }
            }
        })
    }
}