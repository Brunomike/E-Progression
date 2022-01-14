package com.example.e_progression.domain.model

data class ResetPasswordTask(
    val email: String,
    val password: String,
    val confirmPassword:String
)