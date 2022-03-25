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
    suspend fun userLogin(
        username: String,
        password: String,
        country_code: String,
        country_short_name: String,
        device_token: String,
        login_type: String,
        mobile_otp: String,
        facebook_id: String,
        google_id: String,
        name: String,
        email: String,
    ): Response<AuthResponse> {
        return MyApi().userLogin(
            username,
            password,
            country_code,
            country_short_name,
            device_token,
            login_type,
            mobile_otp,
            facebook_id,
            google_id,
            name,
            email,
        )
    }
}