package com.example.myapplication.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    companion object {
        private const val BASE_URL = "https://apix.simplifiedcoding.in/api/"
    }

    fun<Api> buildApi(
        api: Class<Api>
    ) : Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}