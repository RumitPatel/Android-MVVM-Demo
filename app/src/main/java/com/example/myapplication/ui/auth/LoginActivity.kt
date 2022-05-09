package com.example.myapplication.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.data.db.entities.User
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.ui.home.HomeActivity
import com.example.myapplication.util.*
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

        initComponents()
        setListeners()
    }

    private fun initComponents() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        binding.editTextEmail.setText("9978084284")
        binding.editTextPassword.setText("111111")

        viewModel.getLoggedInUser().observe(this) { user ->
            if (user != null) {
                Intent(applicationContext, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }
    }

    private fun setListeners() {
        binding.buttonSignIn.setOnClickListener {
            loginUser()
        }

        binding.textViewSignUp.setOnClickListener {
            startActivity(Intent(applicationContext, SignUpActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        if (isValidDataToLogin()) {

            lifecycleScope.launchWhenResumed {
                try {
                    val authResponse = viewModel.userLogin(email, password)
                    authResponse.result?.let {
                        val user: User = it
                        if (user.auth_token.isNotEmpty()
                        ) {
                            viewModel.saveLoggedInUser(user)
                        } else {
                            binding.rootLayout.snackbar(authResponse.msg!!)
                        }
                    }

                } catch (e: ApiException) {
                    e.printStackTrace()
                    toast("Error: " + e.message)
                } catch (e: NoInternetException) {
                    toast("Error: " + e.message)
                }
            }
        }
    }

    private fun isValidDataToLogin(): Boolean {
        return when {
            binding.editTextEmail.text.toString().trim().isEmpty() -> {
                binding.rootLayout.snackbar("Invalid Email")
                false
            }
            binding.editTextPassword.text.toString().trim().isEmpty() -> {
                binding.rootLayout.snackbar("Invalid Password")
                false
            }
            else -> true
        }
    }
}