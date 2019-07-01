package com.example.serietracking.login

import android.os.Bundle
import android.view.View
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.serietracking.MainActivity
import com.example.serietracking.R
import com.example.serietracking.account.AccountService
import com.example.serietracking.login.dto.CreateSessionLoginRequest
import com.example.serietracking.login.dto.CreateSessionRequest
import com.example.serietracking.login.dto.CreateSessionResponse
import com.example.serietracking.login.dto.RequestTokenResponse
import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.ErrorLoggingCallback
import com.example.serietracking.network.HttpConstants
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response
import android.app.ProgressDialog




class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    fun executeLogin(view: View) {
        val nDialog = ProgressDialog(this@LoginActivity)
        nDialog.setMessage("Loading..")
        nDialog.setTitle("Login")
        nDialog.isIndeterminate = false
        nDialog.setCancelable(true)
        nDialog.show()
        Log.d("log","calling create request token")
        ApiClient.apiInterface.createRequestToken(HttpConstants.API_KEY).enqueue(object: ErrorLoggingCallback<RequestTokenResponse>() {
            override fun onResponse(call: Call<RequestTokenResponse>, response: Response<RequestTokenResponse>) {
                Log.d("log","create request token finished")
                response.body().let { requestTokenResponse ->
                    ApiClient.apiInterface.validateRequestTokenLogin(HttpConstants.API_KEY, CreateSessionLoginRequest(user.text.toString(), pass.text.toString(), requestTokenResponse.requestToken)).enqueue(object: ErrorLoggingCallback<RequestTokenResponse>() {
                        override fun onResponse(call: Call<RequestTokenResponse>, response: Response<RequestTokenResponse>) {
                            ApiClient.apiInterface.createSession(
                                HttpConstants.API_KEY,
                                CreateSessionRequest(requestTokenResponse.requestToken)
                            ).enqueue(object: ErrorLoggingCallback<CreateSessionResponse>() {
                                override fun onResponse(call: Call<CreateSessionResponse>, response: Response<CreateSessionResponse>) {
                                    response.body().let { createSessionResponse ->
                                        if(createSessionResponse!=null){
                                            AccountService.setSessionId(createSessionResponse.sessionId)
                                            nDialog.dismiss()
                                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                            startActivity(intent)
                                        }

                                    }
                                }
                            })
                        }
                    })
                }
            }
        })
    }
}