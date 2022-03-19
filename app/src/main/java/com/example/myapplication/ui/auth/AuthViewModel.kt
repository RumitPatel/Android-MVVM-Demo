package com.example.myapplication.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repository.UserRepository

class AuthViewModel : ViewModel() {
    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    fun onLoginButtonClicked(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid credentials")
            return
        }

        val loginresponse = UserRepository().userLogin(email!!, password!!)
        authListener?.onSuccess(loginresponse)
    }
}