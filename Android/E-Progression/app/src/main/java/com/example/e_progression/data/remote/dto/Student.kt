package com.example.e_progression.data.remote.dto

data class Student(
    val adm_no: Int,
    val createdAt: String,
    val date_joined: String,
    val dob: String,
    val dorm_id: Any?,
    val first_name: String,
    val gender: String,
    val image_link: Any?,
    val kcpe_results: Int,
    val last_name: String,
    val other_names: String?,
    val parent_id: Int,
    val stream_allocated_id: Int,
    val updatedAt: String
)