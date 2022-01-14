package com.example.e_progression.domain.model

data class Results(
    val examType: String,
    val classroom_id: Int,
    val exam_type_id: Int,
    val grade: String,
    val id: Int,
    val marks: Int,
    val status: String,
    val student_id: Int,
    val subject_id: Int,
    val subject_name:String,
    val student_name:String
)