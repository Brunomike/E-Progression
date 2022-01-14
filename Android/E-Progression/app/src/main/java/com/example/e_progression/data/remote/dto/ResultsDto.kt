package com.example.e_progression.data.remote.dto

import com.example.e_progression.domain.model.Results

data class ResultsDto(
    val ExamType: ExamType,
    val Subject: Subject,
    val classroom_id: Int,
    val createdAt: String,
    val exam_type_id: Int,
    val grade: String,
    val id: Int,
    val marks: Int,
    val status: String,
    val student_id: Int,
    val subject_id: Int,
    val updatedAt: String
)
//
//fun ResultsDto.toResults(): Results {
//    return Results(
//        examType = ExamType.exam_name,
//        classroom_id = classroom_id,
//        exam_type_id=exam_type_id,
//        grade = grade,
//        id=id,
//        marks = marks,
//        status = status,
//        student_id=student_id,
//        subject_id = subject_id,
//        subject_name =Subject.subject_title
//
//    )
//}