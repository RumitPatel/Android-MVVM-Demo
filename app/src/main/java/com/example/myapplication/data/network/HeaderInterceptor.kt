package com.example.myapplication.data.network

import android.content.Context
import com.example.myapplication.BuildConfig
import com.example.myapplication.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            /*.addHeader(
                "Authorization",
                "Bearer " + getPreferences(mContext, SP.AUTH_TOKEN).toString()
            )*/
            .addHeader("X-API-KEY", Constants.API_STATIC_KEY)
            .addHeader("device_app_version", (BuildConfig.VERSION_CODE).toString())
            .addHeader("device_type", Constants.DEVICE_TYPE_ANDROID)
            .build()

        return chain.proceed(request)
    }
}