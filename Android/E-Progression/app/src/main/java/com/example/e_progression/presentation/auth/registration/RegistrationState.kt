package com.example.e_progression.presentation.auth.registration

import com.example.e_progression.data.remote.dto.RegistrationResponse

data class RegistrationState(
    val isLoading: Boolean = false,
    val message: RegistrationResponse? = null,
    val error: String = ""

)
