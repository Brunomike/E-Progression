package com.example.e_progression.domain.model

import java.sql.Date

data class Subject(
    val name: String,
    val marks:Int,
    val grade: String,
    val examType:String,
    val admNo:Int
)
