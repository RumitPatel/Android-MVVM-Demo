package com.example.myapplication.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.util.Coroutines

class AuthViewModel : ViewModel() {
    var email: String? = "9999999999"
    var password: String? = "111111"

    var authListener: AuthListener? = null

    fun onLoginButtonClicked(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid credentials")
            return
        }

        Coroutines.main {
            val response = UserRepository().userLogin(
                email!!,
                password!!,
                "91",
                "IN",
                "abc",
                "login_password",
                "",
                "",
                "",
                "",
                ""
            )
            if (response.isSuccessful) {
                authListener?.onSuccess(response.body()?.result)
            } else {
                authListener?.onFailure("Error code: ${response.code()}")
            }
        }
    }
}