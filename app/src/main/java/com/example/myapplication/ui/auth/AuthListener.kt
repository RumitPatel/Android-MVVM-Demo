package com.example.myapplication.ui.auth

import androidx.lifecycle.LiveData

interface AuthListener {
    fun onStarted()
    fun onSuccess(loginresponse: LiveData<String>)
    fun onFailure(message: String)
}