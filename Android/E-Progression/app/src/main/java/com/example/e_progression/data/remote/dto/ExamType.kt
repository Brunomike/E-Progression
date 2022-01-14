package com.example.e_progression.data.remote.dto

import com.example.e_progression.domain.model.Exam

data class ExamType(
    val createdAt: String,
    val exam_description: String,
    val exam_name: String,
    val id: Int,
    val start_date: String,
    val updatedAt: String
)

fun ExamType.toExam(): Exam {
    return Exam(
        id = id,
        exam_name = exam_name,
        exam_description = exam_description,
        start_date = start_date
    )
}