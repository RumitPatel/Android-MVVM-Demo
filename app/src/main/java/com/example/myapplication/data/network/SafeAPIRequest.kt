package com.example.myapplication.data.network

import com.example.myapplication.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeAPIRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val message = StringBuilder()
            val error = response.errorBody()?.string()
            error?.let {
                try {
                    message.append(JSONObject(it).get("msg"))
                } catch (e: JSONException) {
                }
                message.append("\n")
            }

            message.append("Error code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }
}