package com.example.e_progression.presentation.main.results

import com.example.e_progression.domain.model.Exam

data class ExamState(
    val isLoading: Boolean = false,
    val examType: List<Exam> = emptyList(),
    val error: String = ""
)
