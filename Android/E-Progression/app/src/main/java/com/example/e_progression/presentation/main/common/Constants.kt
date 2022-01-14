package com.example.e_progression.presentation.main.common

import com.example.e_progression.R
import com.example.e_progression.presentation.Screen
import com.example.e_progression.presentation.main.components.BottomNavigationItem

object Constants {
    val menus: List<BottomNavigationItem> = listOf(
        BottomNavigationItem("Home", Screen.HomeScreen.route, R.drawable.ic_home),
        BottomNavigationItem("Results", Screen.ResultsScreen.route, R.drawable.ic_home),
        BottomNavigationItem("Fees", Screen.FeesScreen.route, R.drawable.ic_home),
        BottomNavigationItem("News", Screen.NewsScreen.route, R.drawable.ic_home),
        BottomNavigationItem("Profile", Screen.ProfileScreen.route, R.drawable.ic_profile)
    )
}