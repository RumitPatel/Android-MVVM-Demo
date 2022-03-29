package com.example.myapplication.data.repository

import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.entities.User
import com.example.myapplication.data.network.MyApi
import com.example.myapplication.data.network.SafeAPIRequest
import com.example.myapplication.data.network.responses.AuthResponse

class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeAPIRequest() {
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
        email: String
    ): AuthResponse {

        return apiRequest {
            api.userLogin(
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
                email
            )
        }
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}