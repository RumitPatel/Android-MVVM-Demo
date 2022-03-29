package com.example.myapplication.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.db.entities.User
import com.example.myapplication.data.network.responses.AuthResponse
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.util.ApiException
import com.example.myapplication.util.Coroutines

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {
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
            try {
                val authResponse: AuthResponse = repository.userLogin(
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
                authResponse.result?.let {
                    val userList: ArrayList<User> = it
                    if (!userList.isNullOrEmpty()
                        && userList.get(0).firstname.isNotEmpty()
                    ) {
                        authListener?.onSuccess(userList.get(0))
                        repository.saveUser(userList.get(0))
                        return@main
                    } else {
                        authListener?.onFailure(authResponse.msg!!)
                    }
                }
                authListener?.onFailure(authResponse.msg!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            }
        }
    }
}