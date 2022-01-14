package com.example.e_progression.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.e_progression.R
import com.example.e_progression.presentation.auth.ForgotPasswordScreen
import com.example.e_progression.presentation.auth.LoginScreen
import com.example.e_progression.presentation.auth.RegistrationScreen
import com.example.e_progression.presentation.main.*
import com.example.e_progression.presentation.main.components.BottomMenu
import com.example.e_progression.presentation.main.components.BottomNavigationItem
import com.example.e_progression.presentation.main.news_detail.NewsDetailScreen
import com.example.e_progression.presentation.ui.theme.gradientColor1
import com.example.e_progression.presentation.ui.theme.gradientColor2


@RequiresApi(Build.VERSION_CODES.M)
@ExperimentalFoundationApi
@Composable
fun Navigation(navController: NavHostController) {

    val scaffoldState= rememberScaffoldState()
    val menus: List<BottomNavigationItem> = listOf(
        BottomNavigationItem("Home", Screen.HomeScreen.route, R.drawable.ic_home),
        BottomNavigationItem("Results", Screen.ResultsScreen.route, R.drawable.results),
        BottomNavigationItem("Fees", Screen.FeesScreen.route, R.drawable.money),
        BottomNavigationItem("News", Screen.NewsScreen.route, R.drawable.news),
        BottomNavigationItem("Profile", Screen.ProfileScreen.route, R.drawable.ic_profile)
    )
    val bottomItems = listOf(
        Screen.HomeScreen,
        Screen.ResultsScreen,
        Screen.FeesScreen,
        Screen.NewsScreen,
        Screen.NewsItemScreen,
        Screen.ProfileScreen
    )

    //val navController= rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    Scaffold(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        gradientColor1,
                        gradientColor2
                    )
                )
            )
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        bottomBar = {
            Log.d( "AppScreensSections: ",navController.currentDestination?.route.toString())
            Log.d( "AppScreensSections: ",navController.currentDestination?.displayName.toString())
            if (navController.currentDestination?.route in bottomItems.map { item -> item.route+"/{userUUID}" }
            ) {
                BottomMenu(items = menus, navController = navController)
            }
        }
    ) {
        NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
            composable(route = Screen.SplashScreen.route) {
                SplashScreen(navController = navController)
            }
            composable(route = Screen.LoginScreen.route) {
                LoginScreen(navController = navController)
            }
            composable(route = Screen.ForgotPasswordScreen.route) {
                ForgotPasswordScreen(navController = navController)
            }
            composable(route = Screen.RegistrationScreen.route) {
                RegistrationScreen(navController = navController)
            }
            composable(route = Screen.HomeScreen.route + "/{userUUID}") {
                HomeScreen(navController = navController)
            }
            composable(route = Screen.ResultsScreen.route + "/{userUUID}") {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    ResultsScreen(navController = navController)
                }
            }
            composable(route = Screen.FeesScreen.route + "/{userUUID}") {
                FeesScreen(navController = navController)
            }
            composable(route = Screen.NewsScreen.route + "/{userUUID}") {
                NewsScreen(navController = navController)
            }
            composable(route = Screen.ProfileScreen.route + "/{userUUID}") {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ProfileScreen(navController = navController)
                }
            }
            composable(route = Screen.EditProfileScreen.route) {
                //EditProfileScreen(navController = navController)
            }
            composable(route = Screen.EditPasswordScreen.route) {
                //EditPasswordScreen(navController = navController)
            }
            composable(route = Screen.NewsItemScreen.route + "/{newsId}") {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NewsDetailScreen(navController = navController)
                }
            }

        }
    }
}