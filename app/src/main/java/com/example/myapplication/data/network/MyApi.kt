package com.example.myapplication.data.network

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.network.responses.AuthResponse
import com.example.myapplication.data.network.responses.QuotesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApi {

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor,
            headerInterceptor: HeaderInterceptor
        ): MyApi {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder().apply {
                addInterceptor(networkConnectionInterceptor)
                addInterceptor(headerInterceptor)
                if (BuildConfig.DEBUG) {
                    addInterceptor(httpLoggingInterceptor)
                }
            }.build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://subtest.epharmaplatform.com/api_v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("country_code") country_code: String,
        @Field("country_short_name") country_short_name: String,
        @Field("device_token") device_token: String,
        @Field("login_type") login_type: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("register/register_user")
    suspend fun userSignUp(
        @Field("first_name") first_name: String?,
        @Field("mobile") mobile: String?,
        @Field("email") email: String?,
        @Field("country_code") code: String?,
        @Field("country_short_name") countryShortName: String?,
        @Field("password") password: String?,
        @Field("device_type") device_type: String?,
        @Field("version_code_android") temp_version_code: String?
    ): Response<AuthResponse>

    @GET("quotes")
    suspend fun getQuotes(): Response<QuotesResponse>
}