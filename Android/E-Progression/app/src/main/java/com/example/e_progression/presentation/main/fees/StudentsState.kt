package com.example.e_progression.presentation.main.fees

import com.example.e_progression.data.remote.dto.Student

data class StudentsState(
    val isLoading: Boolean = false,
    val students: List<Student> = emptyList(),
    val error: String = ""
)
