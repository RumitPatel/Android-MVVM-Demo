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
import com.example.myapplication.databinding.ActivitySignUpBinding
import com.example.myapplication.ui.home.HomeActivity
import com.example.myapplication.util.ApiException
import com.example.myapplication.util.NoInternetException
import com.example.myapplication.util.snackbar
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignUpActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)


/*
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
*/

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        binding.buttonSignUp.setOnClickListener(View.OnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val passwordConfirm = binding.editTextPasswordConfirm.text.toString().trim()

            //todo validate input

            lifecycleScope.launch {
                try {
                    val authResponse = viewModel.userSignup(name, email, password)
                    authResponse.result?.let {
                        val userList: ArrayList<User> = it
                        if (!userList.isNullOrEmpty() && userList[0].firstname.isNotEmpty()
                        ) {
                            viewModel.saveLoggedInUser(userList[0])
                        } else {
                            binding.root.snackbar(authResponse.msg!!)
                        }
                    }

                } catch (e: ApiException) {
                    e.printStackTrace()
                } catch (e: NoInternetException) {
                    e.printStackTrace()
                }
            }

        })
    }
}