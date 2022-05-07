package com.example.myapplication.ui.auth

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.db.entities.User
import com.example.myapplication.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun getLoggedInUser() = repository.getUser()

    suspend fun userLogin(
        email: String,
        password: String
    ) = withContext(Dispatchers.IO) {
        repository.userLogin(
            email,
            password,
            "91",
            "IN",
            "abcd1234",
            "",
            "",
            "",
            "",
            "",
            email,
            "android",
            "60"
        )
    }

    suspend fun userSignup(
        name: String,
        email: String,
        password: String
    ) = withContext(Dispatchers.IO) {
        repository.userSignup(
            name,
            email,
            email,
            password,
            "91",
            "IN",
            "android",
            "60"
        )
    }

    suspend fun saveLoggedInUser(user: User) = repository.saveUser(user)
}