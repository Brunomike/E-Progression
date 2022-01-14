package com.example.e_progression.presentation

import android.content.Context
import android.content.SharedPreferences
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_progression.R
import com.example.e_progression.common.Constants
import com.example.e_progression.presentation.auth.LoginScreen
import com.example.e_progression.presentation.auth.login.LoginViewModel
import com.example.e_progression.presentation.ui.theme.EProgressionTheme
import com.example.e_progression.presentation.ui.theme.gradientColor1
import com.example.e_progression.presentation.ui.theme.gradientColor2
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    val sharedPreferences=LocalContext.current.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val email=sharedPreferences.getString("email","")
    val password=sharedPreferences.getString("password","")

    // AnimationEffect
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 200,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(1000L)
            if (email=="" && password==""){
                navController.navigate(Screen.LoginScreen.route)
            }else{
                Constants.PARAM_USER_UUID=sharedPreferences.getString("userUUID","").toString()
                Constants.USER_FIRST_NAME=sharedPreferences.getString("name","").toString()
                navController.navigate(Screen.HomeScreen.route+"/${Constants.PARAM_USER_UUID}")
            }

    }

    Box(modifier = Modifier
        .background( brush= Brush.verticalGradient(
        colors = listOf(gradientColor1, gradientColor2)
    )).fillMaxSize()
    ){
    Column(modifier=Modifier
        .align(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier= Modifier
                .clip(CircleShape)
                .width(150.dp)
                .height(150.dp)
                .background(Color.White)
                .border(
                    width = 8.dp, brush = Brush.verticalGradient(
                        colors = listOf(gradientColor1, gradientColor2)
                    ), shape = CircleShape
                )
        )
    }
    }
}

