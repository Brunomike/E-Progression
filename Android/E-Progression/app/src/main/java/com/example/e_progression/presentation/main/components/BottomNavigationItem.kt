package com.example.e_progression.presentation.main.components

import androidx.annotation.DrawableRes

data class BottomNavigationItem(
    val name:String,
    val route:String,
    @DrawableRes val icon: Int,
    val badgeCount:Int=0
)
