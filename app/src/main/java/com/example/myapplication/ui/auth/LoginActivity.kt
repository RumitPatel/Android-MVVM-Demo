package com.example.myapplication.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.entities.User
import com.example.myapplication.data.network.MyApi
import com.example.myapplication.data.network.NetworkConnectionInterceptor
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.ui.home.HomeActivity
import com.example.myapplication.util.hide
import com.example.myapplication.util.show
import com.example.myapplication.util.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(this)
        val repository = UserRepository(api, db)
        val factory = AuthViewModelFactory(repository)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = object : AuthListener {
            override fun onStarted() {
                progress_bar.show()
            }

            override fun onSuccess(user: User?) {
                if (!user!!.firstname.isEmpty()) {
                    toast(user.firstname + " is logged In")
                } else {
                    toast("User Not getting after login")
                }

                progress_bar.hide()
            }

            override fun onFailure(message: String) {
                progress_bar.hide()
                toast("Failure message:$message")
            }
        }

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }
}