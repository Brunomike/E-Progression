package com.example.e_progression.presentation

sealed class Screen(val route:String){
    object SplashScreen:Screen("splash_screen")
    object LoginScreen:Screen("login_screen")
    object RegistrationScreen:Screen("registration_screen")
    object ForgotPasswordScreen:Screen("forgot_password_screen")
    object HomeScreen:Screen("home_screen")
    object ResultsScreen:Screen("results_screen")
    object FeesScreen:Screen("fees_screen")
    object NewsScreen:Screen("news_screen")
    object NewsItemScreen:Screen("news_item_screen")
    object ProfileScreen:Screen("profile_screen")
    object EditProfileScreen:Screen("edit_profile_screen")
    object EditPasswordScreen:Screen("edit_password_screen")
}
