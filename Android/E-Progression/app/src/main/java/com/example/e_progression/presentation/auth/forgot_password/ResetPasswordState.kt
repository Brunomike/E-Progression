package com.example.e_progression.presentation.auth.forgot

import com.example.e_progression.data.remote.dto.PassResetResponse


data class ResetPasswordState(
    val isLoading: Boolean = false,
    val message: PassResetResponse? = null,
    val error: String = ""
)
