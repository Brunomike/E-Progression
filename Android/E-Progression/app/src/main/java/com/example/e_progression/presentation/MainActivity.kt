package com.example.e_progression.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.navigation.compose.rememberNavController
import com.example.e_progression.common.Constants
import com.example.e_progression.presentation.ui.theme.EProgressionTheme
import com.example.e_progression.presentation.ui.theme.gradientColor1
import com.example.e_progression.presentation.ui.theme.gradientColor2
import com.onesignal.OneSignal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    @ExperimentalFoundationApi
     val ONESIGNAL_APP_ID = Constants.ONESIGNAL_APP_ID
    @ExperimentalFoundationApi
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

        setContent {
            EProgressionTheme {
                val scaffoldState= rememberScaffoldState()
                val navController = rememberNavController()
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
                ) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        Navigation(navController)
                        //AppScreensSections(navController)
                    }
                }

            }
        }
    }

}
