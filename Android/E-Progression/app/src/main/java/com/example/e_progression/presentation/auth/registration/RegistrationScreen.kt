package com.example.e_progression.presentation.auth

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.Log
import android.view.animation.OvershootInterpolator
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_progression.R
import com.example.e_progression.common.isOnline
import com.example.e_progression.presentation.Screen
import com.example.e_progression.presentation.auth.registration.RegistrationViewModel
import com.example.e_progression.presentation.ui.theme.*
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun RegistrationScreen(navController: NavController,viewModel:RegistrationViewModel= hiltViewModel()) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val firstNameVal = remember { mutableStateOf("") }
    val lastNameVal = remember { mutableStateOf("") }
    val emailVal = remember { mutableStateOf("") }
    val genderVal = remember { mutableStateOf("") }
    val genderSelection = if (genderVal.value.isBlank()) "Select your Gender" else genderVal.value
    val phoneNoVal = remember { mutableStateOf("") }
    val dobVal = remember { mutableStateOf("") }
    val dobSelection = if (dobVal.value.isBlank()) "Select your Date of Birth" else dobVal.value
    val passwordVal = remember { mutableStateOf("") }
    val confirmPasswordVal = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val dropDownState = remember { mutableStateOf(false) }
    val genderItems = listOf("Male", "Female", "Other", "Prefer not to say")

    val state=viewModel.state.value

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

    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("dd-mm-yyyy")

    val listener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        calendar[Calendar.MONTH] = month
        calendar[Calendar.YEAR] = year
        //calendar.timeInMillis.toString()
        var monthName = ""
        when (month) {
            0 -> {
                monthName = "Jan"
            }
            1 -> {
                monthName = "Feb"
            }
            2 -> {
                monthName = "Mar"
            }
            3 -> {
                monthName = "Apr"
            }
            4 -> {
                monthName = "May"
            }
            5 -> {
                monthName = "Jun"
            }
            6 -> {
                monthName = "Jul"
            }
            7 -> {
                monthName = "Aug"
            }
            8 -> {
                monthName = "Sep"
            }
            9 -> {
                monthName = "Oct"
            }
            10 -> {
                monthName = "Nov"
            }
            11 -> {
                monthName = "Dec"
            }
        }
        val date = "${dayOfMonth}-${monthName}-${year}"
        dobVal.value = date
        //dobVal.value = sdf.format(calendar.time)
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
                    .align(Alignment.Center)

            ) {
                LazyColumn(modifier = Modifier.simpleVerticalScrollbar(state = rememberLazyListState())) {
                    item {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(10.dp)
                        ) {
                            Row(modifier = Modifier) {
                                Text(
                                    text = "Create Account",
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = "App Logo",
                                    modifier = Modifier
                                        .offset(x = 15.dp)
                                        .clip(CircleShape)
                                        .width(50.dp)
                                        .height(50.dp)
                                        .background(img)
                                        .border(
                                            width = 3.dp, brush = Brush.verticalGradient(
                                                colors = listOf(
                                                    gradientColor1, gradientColor2
                                                )
                                            ), shape = CircleShape
                                        )
                                )
                            }


                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "First Name", color = Color.Black)
                            TextField(
                                value = firstNameVal.value,
                                onValueChange = { firstNameVal.value = it },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = TextWhite,
                                    textColor = Color.Black,
                                    placeholderColor = Color.LightGray
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Last Name", color = Color.Black)
                            TextField(
                                value = lastNameVal.value,
                                onValueChange = { lastNameVal.value = it },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = TextWhite,
                                    textColor = Color.Black,
                                    placeholderColor = Color.LightGray,

                                    )
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Email Address", color = Color.Black)
                            TextField(
                                value = emailVal.value,
                                onValueChange = { emailVal.value = it },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = TextWhite,
                                    textColor = Color.Black,
                                    placeholderColor = Color.LightGray
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Email
                                ),
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = null,
                                        tint = Color.Black
                                    )
                                }
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Gender", color = Color.Black)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        dropDownState.value = !dropDownState.value
                                    }
                                    .background(TextWhite)
                                    .height(56.dp)
                            ) {
                                Text(
                                    text = genderSelection,
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .clickable {
                                            dropDownState.value = !dropDownState.value
                                        }
                                        .fillMaxWidth()
                                        .padding(start = 10.dp),
                                    color = Color.Black,
                                )

                                IconButton(
                                    modifier = Modifier.align(Alignment.CenterEnd),
                                    onClick = {
                                        dropDownState.value = !dropDownState.value
                                    },
                                ) {
                                    Icon(
                                        imageVector = if (dropDownState.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                        contentDescription = null,
                                        tint = Color.Black
                                    )
                                }
                                DropdownMenu(expanded = dropDownState.value,
                                    onDismissRequest = {
                                        dropDownState.value = false
                                    }, modifier = Modifier
                                        .clickable {
                                            dropDownState.value = !dropDownState.value
                                        }
                                        .fillMaxWidth(.8f)) {
                                    genderItems.forEach { item ->
                                        DropdownMenuItem(
                                            modifier = Modifier.fillMaxWidth(),
                                            onClick = {
                                                genderVal.value = item
                                                dropDownState.value = !dropDownState.value
                                            }) {
                                            Text(text = item)
                                        }
                                    }
                                }
                            }


                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Phone Number", color = Color.Black)
                            TextField(
                                value = phoneNoVal.value,
                                onValueChange = { phoneNoVal.value = it },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = TextWhite,
                                    textColor = Color.Black,
                                    placeholderColor = Color.LightGray,
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Phone
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Date Of Birth", color = Color.Black)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(TextWhite)
                                    .height(56.dp)
                            ) {
                                Text(
                                    text = dobSelection,
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .fillMaxWidth()
                                        .padding(start = 10.dp),
                                    color = Color.Black,
                                )
                                IconButton(
                                    modifier = Modifier.align(Alignment.CenterEnd),
                                    onClick = {
                                        DatePickerDialog(
                                            context,
                                            listener,
                                            calendar[Calendar.YEAR],
                                            calendar[Calendar.MONTH],
                                            calendar[Calendar.DAY_OF_MONTH]
                                        ).show()

                                    }) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "Date Picker",
                                        tint = Color.Black
                                    )
                                }

                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Password", color = Color.Black)
                            TextField(
                                value = passwordVal.value,
                                onValueChange = { passwordVal.value = it },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = TextWhite,
                                    textColor = Color.Black,
                                    placeholderColor = Color.LightGray
                                ),
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
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Confirm Password", color = Color.Black)
                            TextField(
                                value = confirmPasswordVal.value,
                                onValueChange = { confirmPasswordVal.value = it },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = TextWhite,
                                    textColor = Color.Black,
                                    placeholderColor = Color.LightGray
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()
                            )

                            Spacer(modifier = Modifier.height(20.dp))
                            Button(
                                onClick = {
                                    if (firstNameVal.value.isBlank() && lastNameVal.value.isBlank() && emailVal.value.isBlank() && genderVal.value.isBlank() && phoneNoVal.value.isBlank() && dobVal.value.isBlank() && passwordVal.value.isBlank() && confirmPasswordVal.value.isBlank()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "All fields are required!",
                                                duration=SnackbarDuration.Long
                                            )
                                        }
                                    } else if (firstNameVal.value.isBlank()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "First Name is required!",
                                                duration=SnackbarDuration.Long
                                            )
                                        }
                                    } else if (lastNameVal.value.isBlank()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Last Name is required!",
                                                duration=SnackbarDuration.Long
                                            )
                                        }
                                    } else if (emailVal.value.isBlank()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Email Address is required!",
                                                duration=SnackbarDuration.Long
                                            )
                                        }
                                    } else if (genderVal.value.isBlank()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Select your gender please!",
                                                duration=SnackbarDuration.Long
                                            )
                                        }
                                    } else if (phoneNoVal.value.isBlank()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Phone Number is required!",
                                                duration=SnackbarDuration.Long
                                            )
                                        }
                                    } else if (passwordVal.value.isBlank()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Enter your password!",
                                                duration=SnackbarDuration.Long
                                            )
                                        }
                                    } else if (confirmPasswordVal.value.isBlank()) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = "Please confirm your password!",
                                                duration=SnackbarDuration.Long
                                            )
                                        }
                                    } else {
                                        val checkConnection = isOnline(context)
                                        if (checkConnection) {
                                            viewModel.onBtnSignUpClick(
                                                firstNameVal.value,
                                                lastNameVal.value,
                                                emailVal.value,
                                                genderVal.value,
                                                phoneNoVal.value,
                                                dobVal.value,
                                                passwordVal.value,
                                                confirmPasswordVal.value
                                            )
                                            if (state.message !=null){
                                                val message = state.message.message
                                                    when(message){
                                                        "User Already Exists!"->{
                                                            scope.launch {
                                                                scaffoldState.snackbarHostState.showSnackbar(
                                                                    message = "User Already Exists!",
                                                                    duration=SnackbarDuration.Long
                                                                )
                                                            }
                                                        }
                                                        "Account Created Successfully!"->{
                                                            scope.launch {
                                                                scaffoldState.snackbarHostState.showSnackbar(
                                                                    message = "Account Created Successfully!",
                                                                    duration=SnackbarDuration.Long
                                                                )
                                                            }
                                                            animationState.value=true
                                                        }
                                                        "Error in creating New Account!"->{
                                                            scope.launch {
                                                                scaffoldState.snackbarHostState.showSnackbar(
                                                                    message = "Error in creating New Account!Try again later.",
                                                                    duration=SnackbarDuration.Long
                                                                )
                                                            }
                                                        }
                                                        "Passwords do not meet set criteria!"->{
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
                                                        "Passwords do not match!"->{
                                                            scope.launch {
                                                                scaffoldState.snackbarHostState.showSnackbar(
                                                                    message = "Passwords do not match!",
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
                                }, colors = ButtonDefaults.buttonColors(
                                    backgroundColor = primary,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Sign Up", fontFamily = gothicA1, color = Color.White)
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "Have an Account? Sign In",
                                Modifier.clickable {
                                    navController.navigate(Screen.LoginScreen.route)
                                }, color = Color.Black
                            )
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

//GENDER PHONE NO,DOB,
@Composable
fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 8.dp
): Modifier {
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )

    return drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
        val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f

        // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
        if (needDrawScrollbar && firstVisibleElementIndex != null) {
            val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
            val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
            val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

            drawRect(
                color = Color.Red,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha
            )
        }
    }
}






