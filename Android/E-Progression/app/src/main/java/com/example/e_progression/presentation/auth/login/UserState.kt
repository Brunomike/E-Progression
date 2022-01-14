package com.example.e_progression.presentation.auth.login

import com.example.e_progression.data.remote.dto.LoginResponse
import com.example.e_progression.domain.model.User

data class UserState(
    val isLoading: Boolean = false,
    val user: LoginResponse? = null,
    val error: String = ""
)
