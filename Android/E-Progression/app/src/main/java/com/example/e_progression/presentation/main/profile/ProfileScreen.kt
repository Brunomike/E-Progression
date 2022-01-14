package com.example.e_progression.presentation.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_progression.R
import com.example.e_progression.common.convertDate
import com.example.e_progression.presentation.Screen
import com.example.e_progression.presentation.main.components.BottomNavigationItem
import com.example.e_progression.presentation.main.news.NewsListViewModel
import com.example.e_progression.presentation.main.profile.ProfileItem
import com.example.e_progression.presentation.main.profile.ProfileState
import com.example.e_progression.presentation.main.profile.ProfileViewModel
import com.example.e_progression.presentation.ui.theme.*
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {
    val state = viewModel.state.value
    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences(
        "UserPrefs",
        Context.MODE_PRIVATE
    )

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    val animationState = remember { mutableStateOf(false) }
    if (animationState.value) {
        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 0.7f,
                animationSpec = tween(
                    durationMillis = 100,
                    easing = {
                        OvershootInterpolator(4f).getInterpolation(it)
                    })
            )
            delay(1000L)
            navController.navigate(Screen.LoginScreen.route)
        }
    }

    val profileItems: List<ProfileItem> = listOf(
        ProfileItem(key = "FULL NAME", "${state.profile?.firstName} ${state.profile?.lastName}", R.drawable.person),
        ProfileItem(key = "MOBILE NUMBER", "${state.profile?.phoneNumber}", R.drawable.mobile),
        ProfileItem(key = "EMAIL ADDRESS", "${state.profile?.emailAddress}", R.drawable.email),
        ProfileItem(key = "DATE OF BIRTH", "${state.profile?.dob?.let { convertDate(it) }}", R.drawable.date),
        ProfileItem(key = "GENDER", "${state.profile?.gender}", R.drawable.gender),
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        gradientColor1,
                        gradientColor2
                    )
                )
            )
            .padding(bottom = 100.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .padding(8.dp)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            gradientColor1,
                            gradientColor2
                        )
                    )
                )

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Orange, OrangeYellow4
                            )
                        )
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .align(Alignment.TopCenter)
            ) {
                Image(
                    modifier = Modifier
                        .offset(y = (-10.dp))
                        .width(120.dp)
                        .height(120.dp)
                        .clip(CircleShape)
                        .background(TextWhite)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "User Profile Image"
                )
                    Box(modifier = Modifier
                        .offset(y = 10.dp, x = (-10.dp))
                        .padding(4.dp)
                        .background(Color.White)
                        .clip(RoundedCornerShape(5.dp))
                        .align(Alignment.TopEnd)
                        ,) {
                        IconButton(onClick = {
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            //editor.putStringSet()
                            editor.putString("userUUID", "")
                            editor.putString("name", "")
                            editor.putString("email", "")
                            editor.putString("password", "")
                            editor.commit()

                            animationState.value = true
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.logout),
                                contentDescription = "Logout from the app",
                                tint = Color.Black,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }


                Text(
                    text = state.profile?.emailAddress ?: "",
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 5.dp)
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth()
                    .background(TextWhite)
            ) {
                if (state.profile != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .fillMaxHeight(0.1f)
                            .padding(start = 10.dp)
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Account Information",
                            style = MaterialTheme.typography.h2,
                            color = Color.Black
                        )
                    }
                    LazyColumn(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxHeight(0.9f)
                            .padding(start = 20.dp)
                            .fillMaxWidth()
                    ) {
                        items(profileItems) { item ->
                            ProfileItemComponent(item)
                        }

                        item {


                        }
                    }
                }
                if (state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.Center)
                    )
                }
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}


@Composable
fun ProfileItemComponent(profileItem: ProfileItem) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = profileItem.icon),
            contentDescription = "${profileItem.key} Icon",
            modifier = Modifier.size(40.dp)
        )
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(
                text = "${profileItem.key}",
                style = MaterialTheme.typography.h2,
                color = Color.Black
            )
            Text(text = "${profileItem.value}", color = Color.Black)

        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}
