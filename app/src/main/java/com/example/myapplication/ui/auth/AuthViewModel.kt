package com.example.myapplication.ui.auth

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.db.entities.User
import com.example.myapplication.data.repository.UserRepository

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun getLoggedInUser() = repository.getUser()

    suspend fun userLogin(
        email: String,
        password: String
    ) = repository.userLogin(email, password, "91", "IN", "abcd1234", "", "", "", "", "", email)

    suspend fun userSignup(
        name: String,
        email: String,
        password: String
    ) = repository.userSignup(name, email, email, password, "91", "IN", "android", "50")

    suspend fun saveLoggedInUser(user: User) = repository.saveUser(user)
}