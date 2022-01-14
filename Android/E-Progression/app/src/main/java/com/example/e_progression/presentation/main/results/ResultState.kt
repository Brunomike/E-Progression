package com.example.e_progression.presentation.main.results

import com.example.e_progression.domain.model.Exam
import com.example.e_progression.domain.model.Results

data class ResultState(
    val isLoading: Boolean = false,
    val results: List<Results> = emptyList(),
    val error: String = ""
)
