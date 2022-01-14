package com.example.e_progression.presentation.main.fees

import com.example.e_progression.domain.model.Fees

data class FeesState (
    val isLoading: Boolean = false,
    val records: List<Fees> = emptyList(),
    val error: String = ""
)