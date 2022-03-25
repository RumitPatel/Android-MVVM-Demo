package com.example.myapplication.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.data.db.entities.User
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.util.hide
import com.example.myapplication.util.show
import com.example.myapplication.util.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = object : AuthListener {
            override fun onStarted() {
                progress_bar.show()
            }

            override fun onSuccess(users: ArrayList<User>?) {
                Log.d("ddd", if (!users.isNullOrEmpty()) "not empty" else "empty")
                if (!users.isNullOrEmpty()) {
                    val user: User = users.get(0)
                    if (!user.firstname.isEmpty()) {
                        toast(user.firstname + " is logged In")
                    } else {
                        toast("User Not getting after login")
                    }
                } else {
                    toast("Getting user arrary empty")
                }

                progress_bar.hide()
            }


            override fun onFailure(message: String) {
                progress_bar.hide()
                toast("Failure message:$message")
            }
        }
    }
}