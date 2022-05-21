package com.example.myapplication.repository

import com.example.myapplication.data.network.AuthApi

class AuthRepository(
    private val api: AuthApi
) : BaseRepository() {

    suspend fun login(
        email: String?,
        password: String?
    ) = safeApiCall {
        api.login(email!!, password!!)
    }
}