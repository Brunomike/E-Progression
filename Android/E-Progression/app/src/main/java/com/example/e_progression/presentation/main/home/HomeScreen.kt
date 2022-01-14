package com.example.e_progression.presentation.main

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_progression.R
import com.example.e_progression.common.Constants
import com.example.e_progression.domain.model.Feature
import com.example.e_progression.presentation.main.home.HomeViewModel
import com.example.e_progression.presentation.main.home.components.FeesSection
import com.example.e_progression.presentation.main.home.components.GreetingSection
import com.example.e_progression.presentation.main.home.components.LatestNewsSection
import com.example.e_progression.presentation.main.home.components.ResultsSection
import com.example.e_progression.presentation.ui.theme.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

@ExperimentalFoundationApi
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val newsState=viewModel.latestNewsState.value
    val examsState=viewModel.examState.value
    val news = viewModel.latestNewsState.value.news
    val examTypeList = viewModel.examState.value.examType
    val name = Constants.USER_FIRST_NAME

    val feeRecords=viewModel.feesState.value.records
    var credit:Int=0
    var debit:Int=0

    feeRecords.forEach{fee->
        credit+=fee.credit.toFloat().roundToInt()
        debit+=fee.debit.toFloat().roundToInt()
    }
    val paid: Double = debit.toDouble()
    val balance: Double =(credit-debit).toDouble()


    val examsList1 = ArrayList<Feature>()
    if (examTypeList.isNotEmpty()) {
        examTypeList.forEach { type ->
            examsList1.add(
                Feature(
                    type.exam_name, R.drawable.results,
                    Beige1,
                    Beige2,
                    Beige3
                )
            )
        }
    }


    val examsList: ArrayList<Feature> = arrayListOf(
        Feature(
            "Opening Exam", R.drawable.results,
            LightGreen1,
            LightGreen2,
            LightGreen3
        ),
        Feature(
            title = "Indexing Exam",
            R.drawable.results,
            BlueViolet1,
            BlueViolet2,
            BlueViolet3
        ),
        Feature(
            title = "Mid-Term Exam",
            R.drawable.results,
            LightGreen1,
            LightGreen2,
            LightGreen3
        ),
        Feature(
            title = "Regional Exam",
            R.drawable.results,
            OrangeYellow1,
            OrangeYellow2,
            OrangeYellow3
        ),
        Feature(
            title = "Closing Exam",
            R.drawable.results,
            Beige1,
            Beige2,
            Beige3
        )
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
            .padding(5.dp),
    ) {
        Column() {
            LazyColumn() {
                item {
                    GreetingSection(name = name)
                }

                item {
                    if (news != null) {
                        LatestNewsSection(color = danger, news,navController)
                    }
                }

                item {
                    FeesSection(color = warning, paid = paid, balance = balance)
                }

            }

            Box(modifier = Modifier.padding(bottom = 5.dp)) {
                if (examsList1.isNotEmpty()){
                    ResultsSection(features = examsList1, navController = navController)
                }
            }
        }
        if (newsState.error.isNotBlank() ||examsState.error.isNotBlank()) {
            Text(
                text = newsState.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

        if (newsState.isLoading ||examsState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

    }

}


