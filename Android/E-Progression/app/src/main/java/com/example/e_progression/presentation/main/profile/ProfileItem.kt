package com.example.e_progression.presentation.main.profile

import androidx.annotation.DrawableRes

data class ProfileItem(
    val key:String,
    val value:String,
    @DrawableRes val icon: Int
)
