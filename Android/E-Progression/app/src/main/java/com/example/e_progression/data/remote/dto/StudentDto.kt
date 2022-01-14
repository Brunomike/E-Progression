package com.example.e_progression.data.remote.dto

data class StudentDto(
    val Parent: Parent,
    val adm_no: Int,
    val createdAt: String,
    val date_joined: String,
    val dob: String,
    val dorm_id: Any,
    val first_name: String,
    val gender: String,
    val image_link: Any,
    val kcpe_results: Int,
    val last_name: String,
    val other_names: String?,
    val parent_id: Int,
    val stream_allocated_id: Int,
    val updatedAt: String
)

fun StudentDto.toStudent(): Student {
    return Student(
        adm_no = adm_no,
        date_joined = date_joined,
        dob = dob,
        first_name = first_name,
        gender = gender,
        kcpe_results = kcpe_results,
        last_name = last_name,
        other_names = other_names?:null,
        createdAt = createdAt,
        dorm_id = dorm_id,
        parent_id = parent_id,
        image_link = image_link,
        stream_allocated_id = stream_allocated_id,
        updatedAt = updatedAt
    )
}