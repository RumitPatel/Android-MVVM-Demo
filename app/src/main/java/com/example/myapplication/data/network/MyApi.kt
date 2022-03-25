package com.example.myapplication.data.network

import com.example.myapplication.data.network.responses.AuthResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyApi {

    @FormUrlEncoded
    @POST("login/post_login")
    suspend fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("country_code") country_code: String,
        @Field("country_short_name") country_short_name: String,
        @Field("device_token") device_token: String,
        @Field("login_type") login_type: String,
        @Field("mobile_otp") mobile_otp: String,
        @Field("facebook_id") facebook_id: String,
        @Field("google_id") google_id: String,
        @Field("name") name: String,
        @Field("email") email: String
    ) : Response<AuthResponse>

    companion object{
        operator fun invoke() : MyApi{
            return Retrofit.Builder()
                .baseUrl("https://dev.epharmaplatform.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}