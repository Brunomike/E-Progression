package com.example.e_progression.presentation.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.example.e_progression.data.remote.dto.Student
import com.example.e_progression.presentation.auth.components.Dropdown
import com.example.e_progression.presentation.main.fees.FeesListItem
import com.example.e_progression.presentation.main.fees.FeesViewModel
import com.example.e_progression.presentation.ui.theme.gradientColor1
import com.example.e_progression.presentation.ui.theme.gradientColor2
import com.example.e_progression.presentation.ui.theme.gradientColor5
import com.example.e_progression.presentation.ui.theme.gray
import kotlin.math.roundToInt

@Composable
fun FeesScreen(navController: NavController, viewModel: FeesViewModel = hiltViewModel()) {
    val state = viewModel.state.value
    val feeRecords = state.records
    val studentsState = viewModel.studentsState.value
    val studentsRecords = studentsState.students
    var balance: Int = 0
    var credit: Int = 0
    var debit: Int = 0

    feeRecords.forEachIndexed { index, fee ->
        credit += fee.credit.toFloat().roundToInt()
        debit += fee.debit.toFloat().roundToInt()
    }

    balance=credit-debit

    Log.d("FEES: ", state.records.toString())
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
                .clip(RoundedCornerShape(30.dp))
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 100.dp)
                .fillMaxSize()
//                .background(TextWhite)
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(gray)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = "Fee Payment History",
                                style = MaterialTheme.typography.h1,
//                                color = Color.Black
                            )
                            Text(
                                text = "Balance :Ksh.${balance}",
                                style = MaterialTheme.typography.h2,

                                )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.money),
                            contentDescription = "News Icon",
                            tint = Color.White,
                            modifier = Modifier
                                .size(50.dp)
                                .offset(x = 40.dp)
                        )
                    }

                }
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    studentsRecords.forEach { student ->
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(15.dp))
                            ) {
                                Dropdown(
                                    text = "${student.first_name}'s Payment History",
                                    initiallyOpened = if (studentsRecords.size == 1) true else false
                                ) {
                                    if (feeRecords.isEmpty()) {
                                        Box(
                                            modifier = Modifier
                                                .background(Color.White)
                                                .fillMaxWidth(.95f)
                                                .padding(16.dp)
                                        ) {
                                            Text(
                                                text = "No Fees paid for ${student.first_name} ${student.last_name}",
                                                style = MaterialTheme.typography.body1,
                                                modifier = Modifier.padding(10.dp),
                                                color = Color.Black
                                            )
                                        }
                                    } else {
                                        Column(
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .background(gradientColor5)
                                        ) {
                                            feeRecords.forEach { fee ->
                                                if (fee.studentAdmNo == student.adm_no) {
                                                    FeesListItem(fees = fee)
                                                    Spacer(modifier = Modifier.height(8.dp))
                                                }
                                            }
                                        }
                                    }

                                }

                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }

                }

            }

        }

        if (state.error.isNotBlank() || studentsState.error.isNotBlank()) {
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

        if (state.isLoading || studentsState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun StudentItem(student: Student) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(text = "${student.first_name} ${student.last_name}'s Fee Payment History")
    }
}