package com.example.myapplication.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.db.entities.User
import com.example.myapplication.data.network.responses.AuthResponse
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.util.ApiException
import com.example.myapplication.util.Coroutines
import com.example.myapplication.util.NoInternetException

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var name: String? = "Rumit"
    var email: String? = "9999999999"
    var password: String? = "111111"
    var passwordConfirm: String? = "111111"

    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

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
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }
    }

    fun onLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignup(view: View) {
        Intent(view.context, SignUpActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignupButtonClicked(view: View) {
        authListener?.onStarted()

        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Name is required")
            return
        }

        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email is required")
            return
        }

        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Please enter password")
            return
        }

        if (password != passwordConfirm) {
            authListener?.onFailure("Password does not match")
            return
        }

        Coroutines.main {
            try {
                val authResponse: AuthResponse = repository.userSignup(
                    name!!,
                    email!!,
                    email!!,
                    password!!,
                    "91",
                    "IN",
                    "",
                    "android",
                    "51"
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
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }
    }
}