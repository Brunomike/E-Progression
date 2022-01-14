package com.example.e_progression.presentation.main.profile

import com.example.e_progression.domain.model.User
import kotlinx.coroutines.flow.emptyFlow

data class ProfileState(
    val isLoading: Boolean = false,
    val profile: User? = null,
    val error: String = ""
)
