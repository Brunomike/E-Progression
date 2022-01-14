package com.example.e_progression.presentation.auth

import android.content.res.Resources
import android.os.Build
import android.util.Log
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_progression.R
import com.example.e_progression.common.isOnline
import com.example.e_progression.presentation.Screen
import com.example.e_progression.presentation.auth.forgot.ResetPasswordViewModel
import com.example.e_progression.presentation.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ResetPasswordViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val emailAddress = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val passVisibility = remember { mutableStateOf(false) }
    val myIcons = Icons.Rounded
    val context = LocalContext.current

    val state = viewModel.state.value

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    val animationState=remember{mutableStateOf(false)}
    if (animationState.value){
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
            navController.navigate(Screen.LoginScreen.route)
        }
    }


    val height: Int = Resources.getSystem().getDisplayMetrics().heightPixels
    val width: Int = Resources.getSystem().getDisplayMetrics().widthPixels

    val deviceHeight = convertPixelsToDp(height.toFloat(), LocalContext.current)
    val deviceWidth = convertPixelsToDp(width.toFloat(), LocalContext.current)

    var foundWidth: Int = 0
    if (deviceWidth >= 240.0 && deviceWidth < 360.0) {
        foundWidth = 260
    } else if (deviceWidth >= 360.0) {
        foundWidth = 300
    }

    Scaffold(scaffoldState = scaffoldState) {
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
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(16.dp)
                    .width(foundWidth.dp)
                    .height(450.dp)
                    .align(Alignment.Center)

            ) {
                LazyColumn() {
                    item {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "Password Reset",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Email Address", color = Color.Black)
                            TextField(
                                value = emailAddress.value,
                                onValueChange = { emailAddress.value = it },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = TextWhite,
                                    textColor = Color.Black,
                                    placeholderColor = Color.LightGray
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Password", color = Color.Black)
                            TextField(
                                value = password.value, onValueChange = { password.value = it },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = TextWhite,
                                    textColor = Color.Black,
                                    placeholderColor = Color.LightGray
                                ),
                                trailingIcon = {
                                    IconButton(onClick = {
                                        passVisibility.value =
                                            passVisibility.value != true
                                    }) {
                                        Icon(
                                            painter = painterResource(id = if (passVisibility.value) R.drawable.visible else R.drawable.invisible),
                                            contentDescription = "Password Visibility",
                                            tint = Color.DarkGray, modifier = Modifier.size(20.dp)
                                        )
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                visualTransformation = if (passVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Confirm Password", color = Color.Black)
                            TextField(
                                value = confirmPassword.value,
                                onValueChange = { confirmPassword.value = it },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = TextWhite,
                                    textColor = Color.Black,
                                    placeholderColor = Color.LightGray
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                visualTransformation = if (passVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(
                                onClick = {
                                    if (emailAddress.value.isBlank() && password.value.isBlank() && confirmPassword.value.isBlank()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "All Fields are Required!",
                                                duration=SnackbarDuration.Long
                                            )
                                        }
                                    } else if (emailAddress.value.isBlank()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Enter your Email Address",
                                                duration=SnackbarDuration.Long
                                            )
                                        }
                                    } else if (password.value.isBlank()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Enter your password!",
                                                duration=SnackbarDuration.Long
                                            )
                                        }
                                    } else if (confirmPassword.value.isBlank()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Confirm your password!",
                                                duration=SnackbarDuration.Long
                                            )
                                        }


                                    } else {
                                        val connectionCheck = isOnline(context)
                                        if (connectionCheck) {
                                            //scope.launch(Dispatchers.IO) {
                                                viewModel.onBtnSubmitPress(
                                                    emailAddress.value,
                                                    password.value,
                                                    confirmPassword.value
                                                )
                                            //}
                                            if (state.message != null) {
                                                val message = state.message.message
                                                when (message) {
                                                    "Password Changed Successfully!" -> {
                                                        scope.launch {
                                                            scaffoldState.snackbarHostState.showSnackbar(
                                                                message = "Password Changed Successfully!",
                                                                duration=SnackbarDuration.Long
                                                            )
                                                        }
                                                        animationState.value=true
                                                        //navController.navigate(Screen.LoginScreen.route)

                                                    }
                                                    "Passwords do not meet set criteria!" -> {
                                                        scope.launch {
                                                            val message="Password must be 8-32 characters\n" +
                                                                    "Password must contain 1 upper case and 1 lower case letter\n" +
                                                                    "Password must contain at least 1 digit\n" +
                                                                    "Password must contain at least one special character"
                                                            val snackbarResult =scaffoldState.snackbarHostState.showSnackbar(
                                                                message = message,
                                                                actionLabel = "",
                                                                duration = SnackbarDuration.Long
                                                            )
                                                            when(snackbarResult){
                                                                SnackbarResult.Dismissed -> {
                                                                    Log.d(
                                                                        "ForgotPasswordScreen: ","Dismissed"
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                    "Passwords do not match!" -> {
                                                        scope.launch {
                                                            scaffoldState.snackbarHostState.showSnackbar(
                                                                message = "Passwords do not match!",
                                                                duration=SnackbarDuration.Long
                                                            )
                                                        }
                                                    }
                                                    "User Not Found!" -> {
                                                        scope.launch {
                                                            scaffoldState.snackbarHostState.showSnackbar(
                                                                message = "User Not Found!",
                                                                duration=SnackbarDuration.Long
                                                            )
                                                        }
                                                    }
                                                }
                                            }

                                        } else {
                                            scope.launch {
                                                scaffoldState.snackbarHostState.showSnackbar(message = "No Internet Connection!",duration=SnackbarDuration.Long)
                                            }
                                        }

                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = primary,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Submit", fontFamily = gothicA1, color = Color.White)
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                IconButton(onClick = {
                                    navController.navigate(Screen.LoginScreen.route)
                                }) {
                                    Icon(
                                        myIcons.ArrowBack,
                                        contentDescription = "Icon",
                                        tint = Color.Black
                                    )
                                }
                                Text(text = "Back",
                                    Modifier
                                        .clickable {
                                            navController.navigate(Screen.LoginScreen.route)
                                        }
                                        .padding(start = 5.dp), color = Color.Black)
                            }

                        }
                    }
                }
                if (state.error.isNotBlank()) {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = state.error,
                            duration = SnackbarDuration.Long
                        )
                    }
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