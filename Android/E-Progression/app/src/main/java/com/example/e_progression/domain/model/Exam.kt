package com.example.e_progression.domain.model

import com.example.e_progression.data.remote.dto.ExamType

data class Exam(
    val id: Int,
    val exam_description: String,
    val exam_name: String,
    val start_date: String
)
