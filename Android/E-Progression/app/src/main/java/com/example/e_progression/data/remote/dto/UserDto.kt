package com.example.e_progression.data.remote.dto

import com.example.e_progression.domain.model.User
import java.text.SimpleDateFormat


data class UserDto(
    val uuid: String,
    val email_address: String,
    val first_name: String,
    val last_name: String,
    val other_names: String?=null,
    val phone_number: String,
    val user_gender: String,
    val user_role: String,
    val user_status: String,
    val user_profile_image_link: String?=null,
    val year_of_birth: String
)
val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")
fun UserDto.toUser(): User {
    return User(
        uuid = uuid,
        emailAddress = email_address,
        firstName = first_name,
        lastName = last_name,
        otherNames = other_names,
        phoneNumber = phone_number,
        gender = user_gender,
        imageURL = user_profile_image_link,
        dob = year_of_birth
    )
}