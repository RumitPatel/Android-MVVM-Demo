package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.network.MyApi
import com.example.myapplication.data.network.responses.AuthResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    suspend fun userLogin(email: String, password: String): Response<AuthResponse> {
        return MyApi().userLogin(email, password)
    }
}