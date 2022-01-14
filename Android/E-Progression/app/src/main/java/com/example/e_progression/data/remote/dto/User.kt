package com.example.e_progression.data.remote.dto

data class User(
    val email_address: String,
    val first_name: String,
    val last_name: String,
    val other_names: Any,
    val phone_number: String,
    val user_gender: String,
    val user_password: String,
    val user_profile_image_link: String,
    val user_role: String,
    val user_status: String,
    val uuid: String,
    val year_of_birth: String
)