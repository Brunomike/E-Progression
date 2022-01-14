package com.example.e_progression.domain.model

data class News (
val id:Int,
val newsTitle:String,
val newsSubTitle:String,
val newsContent:String,
val newsCategory:String,
val datePosted:String,
val newsImgUrl:String
)