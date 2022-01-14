package com.example.e_progression.presentation.main.news_detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_progression.R
import com.example.e_progression.common.convertDate
import com.example.e_progression.presentation.ui.theme.TextWhite
import com.example.e_progression.presentation.ui.theme.gradientColor1
import com.example.e_progression.presentation.ui.theme.gradientColor2

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsDetailScreen(navController: NavController, viewModel: NewsItemViewModel = hiltViewModel()) {
    val state = viewModel.state.value
    val myIcons = Icons.Rounded
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
                .padding(bottom = 100.dp)
                .fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()){
                item {
                    Box (modifier = Modifier.fillMaxSize()){
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .align(Alignment.TopCenter)
                                .background(Color.LightGray)
                        ) {
                            Image(painter = painterResource(id = R.drawable.logo), contentDescription = null, modifier = Modifier
                                .fillMaxSize()
                                .zIndex(1f))
                            Box(modifier = Modifier
                                .width(60.dp)
                                .zIndex(2f)
                                .offset(y = 20.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .clickable {
                                    navController.navigateUp()
                                }
                                .background(
                                    TextWhite
                                )){
                                IconButton(
                                    onClick = { navController.navigateUp() },
                                ) {
                                    Icon(
                                        myIcons.KeyboardArrowLeft,
                                        contentDescription = "Icon",
                                        tint = Color.Black,
                                        modifier = Modifier.size(40.dp)
                                    )
                                }
                            }

                        }

                    }

                }

                item {
                    Box(modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .align(Alignment.BottomCenter)
                        .fillMaxHeight()
                        //.fillMaxHeight(.7f)
                    ) {
                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(15.dp)
                        ) {

                            state.news?.let {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = it.newsTitle,
                                        style = MaterialTheme.typography.h1,
                                        color = Color.Black,
                                        fontSize = 40.sp
                                    )
                                }
                                Text(
                                    text = state.news.newsSubTitle,
                                    style = MaterialTheme.typography.h2,
                                    color = Color.Black
                                )
                                Text(text = convertDate(it.datePosted),style=MaterialTheme.typography.body1, color = Color.DarkGray)

                                Spacer(modifier = Modifier.height(5.dp))
                                Text(text = state.news.newsContent, color = Color.Black, modifier=Modifier.padding(10.dp))
                            }
                        }
                    }
                }


            }



        }
    }
}

