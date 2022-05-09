package com.example.myapplication.data.network.responses

import com.example.myapplication.data.db.entities.User

data class AuthResponse(
    val status: String?,
    val msg: String?,
    val result: User?
)