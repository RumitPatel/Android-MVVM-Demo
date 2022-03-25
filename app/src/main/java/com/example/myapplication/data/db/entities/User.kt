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
    var email_verified_at: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    val user_id: String,
    val auth_token: String,
    val mobilenumber: String,
    val country_code: String,
    val country_short_name: String,
    val firstname: String,
    val city: String,
    val cart_count: String,
    val gender: String,
    val age: String,
    val mobile: String,
    val login_type:String
) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}