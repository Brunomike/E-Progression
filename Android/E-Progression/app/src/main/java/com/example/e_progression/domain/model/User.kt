package com.example.e_progression.domain.model

data class User(
    val uuid: String,
    val emailAddress: String,
    val firstName: String,
    val lastName: String,
    val otherNames: String?=null,
    val phoneNumber: String,
    val gender: String,
    val imageURL: String?=null,
    val dob: String
)