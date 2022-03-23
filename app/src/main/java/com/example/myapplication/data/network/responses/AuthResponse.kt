package com.example.myapplication.data.network.responses

import com.example.myapplication.data.db.entities.User

data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?
)