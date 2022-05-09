package com.example.myapplication.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity
data class User(
    var id: Int? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    val user_id: String,
    val auth_token: String,
    val firstname: String,
    val mobilenumber: String,
    val country_code: String,
    val cart_count: String

) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}