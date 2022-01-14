package com.example.e_progression.presentation.auth

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_progression.R
import com.example.e_progression.common.Constants
import com.example.e_progression.common.isOnline
import com.example.e_progression.presentation.Screen
import com.example.e_progression.presentation.auth.login.LoginViewModel
import com.example.e_progression.presentation.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CommitPrefEdits")
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val myIcons = Icons.Rounded
    val emailVal = remember { mutableStateOf("") }
    val passwordVal = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }

    val state = viewModel.state.value

    val sharedPreferences:SharedPreferences= LocalContext.current.getSharedPreferences("UserPrefs",Context.MODE_PRIVATE)

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    val animationState=remember{mutableStateOf(false)}
    if (animationState.value){
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
            navController.navigate(Screen.HomeScreen.route+"/${Constants.PARAM_USER_UUID.toString()}")
        }
    }

    val height: Int = Resources.getSystem().displayMetrics.heightPixels
    val width: Int = Resources.getSystem().displayMetrics.widthPixels

    val deviceHeight = convertPixelsToDp(height.toFloat(), LocalContext.current)
    val deviceWidth = convertPixelsToDp(width.toFloat(), LocalContext.current)

    var foundWidth: Int = 0
    if (deviceWidth >= 240.0 && deviceWidth < 360.0) {
        foundWidth = 260
    } else if (deviceWidth >= 360.0) {
        foundWidth = 300
    }

    var iconSize: Int = 0
    if (deviceWidth >= 240.0 && deviceWidth < 360.0) {
        iconSize = 80
    } else if (deviceWidth >= 360.0) {
        iconSize = 100
    }

    var imageOffset: Int = 0
    if (deviceWidth >= 240.0 && deviceWidth < 360.0) {
        imageOffset = 90
    } else if (deviceWidth >= 360.0) {
        imageOffset = 100
    }

    //Log.d("LoginScreen: ", deviceWidth.toString())

    Scaffold(
        scaffoldState = scaffoldState
    ) {
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
//              .height(450.dp)
                    .align(Alignment.Center)
            ) {
                LazyColumn() {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "App Logo",
                            modifier = Modifier
                                .offset(x = (imageOffset).dp)
                                .clip(CircleShape)
                                .align(Alignment.TopCenter)
                                .width(iconSize.dp)
                                .height(iconSize.dp)
                                .background(img)
                                .border(
                                    width = 4.dp, brush = Brush.verticalGradient(
                                        colors = listOf(
                                            gradientColor1, gradientColor2
                                        )
                                    ), shape = CircleShape
                                )
                        )
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "Sign In",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Email Address", color = Color.Black)
                            TextField(
                                value = emailVal.value,
                                onValueChange = { emailVal.value = it },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = TextWhite,
                                    textColor = Color.Black,
                                    placeholderColor = Color.LightGray,
                                    //focusedIndicatorColor = gradientColor2
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                modifier = Modifier.clip(RoundedCornerShape(3.dp))
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Password", color = Color.Black)
                            TextField(
                                value = passwordVal.value,
                                onValueChange = { passwordVal.value = it },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = TextWhite,
                                    textColor = Color.Black,
                                    placeholderColor = Color.LightGray,
                                    //focusedIndicatorColor = gradientColor2
                                ),
                                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    capitalization = KeyboardCapitalization.None
                                ),
                                modifier = Modifier.clip(RoundedCornerShape(3.dp)),
                                trailingIcon = {
                                    IconButton(onClick = {
                                        passwordVisibility.value =
                                            passwordVisibility.value != true
                                    }) {
                                        Icon(
                                            painter = painterResource(id = if (passwordVisibility.value) R.drawable.visible else R.drawable.invisible),
                                            contentDescription = "Password Visibility",
                                            tint = Color.DarkGray,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Forgot Password?", Modifier.clickable {
                                navController.navigate(Screen.ForgotPasswordScreen.route)
                            }, color = Color.Black)
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(
                                onClick = {
                                    if (emailVal.value.isBlank() && passwordVal.value.isBlank()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Email Address and Password are required!",
                                                duration=SnackbarDuration.Long
                                            )
                                        }
                                    } else if (emailVal.value.isBlank()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Enter your Email Address",
                                                duration=SnackbarDuration.Long
                                            )
                                        }
                                    } else if (passwordVal.value.isBlank()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Password Required",
                                                duration=SnackbarDuration.Long
                                            )

                                        }
                                    } else {
                                        val connectionCheck = isOnline(context)
                                        if (connectionCheck) {
                                            scope.launch(Dispatchers.IO) {
                                                viewModel.onBtnSignUpPress(
                                                    emailVal.value,
                                                    passwordVal.value
                                                )
                                                Log.d("AFTER API CALL: ", state.user.toString())
                                            }
                                            Log.d("AFTER API CALL: ", state.user.toString())
                                            if (state.user!=null) {
                                                if (state.user.message == "User Found!") {
                                                    val uuid = state.user.user?.uuid
                                                    Constants.PARAM_USER_UUID = uuid.toString()
                                                    Constants.USER_FIRST_NAME= state.user.user?.first_name.toString()
                                                    Constants.PARAM_USER_EMAIL= state.user.user?.email_address.toString()
                                                    Constants.PARAM_USER_PASSWORD= state.user.user?.user_password.toString()

                                                    val editor:SharedPreferences.Editor=sharedPreferences.edit()
                                                    editor.putString("userUUID",uuid.toString())
                                                    editor.putString("name",state.user.user?.first_name)
                                                    editor.putString("email",state.user.user?.email_address)
                                                    editor.putString("password",state.user.user?.user_password)
                                                    editor.commit()

                                                    animationState.value = true

                                                } else if (state.user.message == "Admin Found!") {
                                                    scope.launch {
                                                        scaffoldState.snackbarHostState.showSnackbar(
                                                            message = "Admin Found.Visit the website to access the admin dashboard.",
                                                            duration=SnackbarDuration.Long
                                                        )
                                                    }
                                                } else if (state.user.message == "Wrong Password!") {
                                                    scope.launch {
                                                        scaffoldState.snackbarHostState.showSnackbar(
                                                            message = "Wrong Password.Enter the Correct Password.",
                                                            duration=SnackbarDuration.Long
                                                        )
                                                    }
                                                } else if (state.user.message == "Account Disabled!") {
                                                    scope.launch {
                                                        scaffoldState.snackbarHostState.showSnackbar(
                                                            message = "Account Disabled.Contact the Admin.",
                                                            duration=SnackbarDuration.Long
                                                        )
                                                    }
                                                } else if (state.user.message == "User Not Found!") {
                                                    scope.launch {
                                                        val snackbarResult =
                                                            scaffoldState.snackbarHostState.showSnackbar(
                                                                message = "User Not Found",
                                                                actionLabel = "Create Account?",
                                                                duration = SnackbarDuration.Long
                                                            )

                                                        when (snackbarResult) {
                                                            SnackbarResult.Dismissed -> Log.d(
                                                                "SnackbarDemo",
                                                                "Dismissed"
                                                            )
                                                            SnackbarResult.ActionPerformed -> {
                                                                navController.navigate(Screen.RegistrationScreen.route)
                                                            }
                                                        }

                                                    }
                                                }
                                            }
                                        } else {
                                            scope.launch {
                                                scaffoldState.snackbarHostState.showSnackbar(message = "No Internet Connection!")
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
                                Text(text = "Sign In", fontFamily = gothicA1, color = Color.White)
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = "Need an Account? Sign Up", Modifier.clickable {
                                navController.navigate(Screen.RegistrationScreen.route)
                            }, color = Color.Black)
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



fun convertPixelsToDp(px: Float, context: Context): Float {
    return px / (context.getResources()
        .getDisplayMetrics().densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}


