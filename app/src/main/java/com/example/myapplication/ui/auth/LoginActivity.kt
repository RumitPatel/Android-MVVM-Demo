package com.example.myapplication.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.data.db.entities.User
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.ui.home.HomeActivity
import com.example.myapplication.util.ApiException
import com.example.myapplication.util.NoInternetException
import com.example.myapplication.util.snackbar
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)


        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        binding.buttonSignIn.setOnClickListener(View.OnClickListener {
            loginUser()
        })

        binding.textViewSignUp.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        })
    }

    private fun loginUser() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        if(isValidDataToLogin()) {

            lifecycleScope.launch {
                try {
                    val authResponse = viewModel.userLogin(email, password)
                    authResponse.result?.let {
                        val userList: ArrayList<User> = it
                        if (!userList.isNullOrEmpty() && userList[0].firstname.isNotEmpty()
                        ) {
                            viewModel.saveLoggedInUser(userList[0])
                        } else {
                            binding.rootLayout.snackbar(authResponse.msg!!)
                        }
                    }

                } catch (e: ApiException) {
                    e.printStackTrace()
                } catch (e: NoInternetException) {
                    e.printStackTrace()
                }
            }        }
    }

    private fun isValidDataToLogin():Boolean {
        return true//todo validation pending
    }
}