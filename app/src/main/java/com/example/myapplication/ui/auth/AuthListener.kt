package com.example.myapplication.ui.auth

import androidx.lifecycle.LiveData
import com.example.myapplication.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(users: ArrayList<User>?)
    fun onFailure(message: String)
}