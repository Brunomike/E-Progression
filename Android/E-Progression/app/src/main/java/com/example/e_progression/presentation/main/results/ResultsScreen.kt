package com.example.e_progression.presentation.main

import android.graphics.fonts.FontStyle
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_progression.R
import com.example.e_progression.presentation.auth.components.Dropdown
import com.example.e_progression.presentation.main.results.ResultScreeItem
import com.example.e_progression.presentation.main.results.ResultsViewModel
import com.example.e_progression.presentation.ui.theme.danger
import com.example.e_progression.presentation.ui.theme.gradientColor1
import com.example.e_progression.presentation.ui.theme.gradientColor2

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ResultsScreen(navController: NavController, viewModel: ResultsViewModel = hiltViewModel()) {
    val state = viewModel.state.value
    val resultsState = viewModel.resultState.value
    val studentsState = viewModel.studentsState.value
    val examTypeList = state.examType
    val resultsList = resultsState.results
    val studentsList = studentsState.students
    Box(
        modifier = Modifier
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
                .clip(RoundedCornerShape(30.dp))
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 100.dp)
                .fillMaxSize()
//                .background(TextWhite)
        ) {
            if (examTypeList.isNotEmpty() && studentsList.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(danger)
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = "Exam Results History",
                                    style = MaterialTheme.typography.h1,
                                    color = Color.White
                                )
                                Text(
                                    text = "Exam Count :${examTypeList.size}",
                                    style = MaterialTheme.typography.h2,
                                    color = Color.White
                                )
                            }
                            Icon(
                                painter = painterResource(id = R.drawable.test),
                                contentDescription = "Exam Icon",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(50.dp)
                                    .offset(x = 40.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(modifier = Modifier.clip(RoundedCornerShape(5.dp))) {
                        studentsList.forEachIndexed { index, student ->
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(15.dp))
                                ) {
                                    Dropdown(text = "${student.first_name}'s Results",initiallyOpened=if (studentsList.size==1) true else false) {

                                        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                            examTypeList.forEach { type ->
                                                Dropdown(text = type.exam_name, initiallyOpened = false) {
                                                    ResultScreeItem(
                                                        type = type,
                                                        results = resultsList,
                                                        student = student
                                                    )
                                                    Spacer(modifier = Modifier.height(10.dp))
                                                }
                                        }
                                        }
                                    }

                                }

                            }
                            item { Spacer(modifier = Modifier.height(10.dp)) }

                        }
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



