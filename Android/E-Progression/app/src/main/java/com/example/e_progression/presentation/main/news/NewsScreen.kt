package com.example.e_progression.presentation.main

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_progression.R
import com.example.e_progression.presentation.Screen
import com.example.e_progression.presentation.main.news.NewsListItem
import com.example.e_progression.presentation.main.news.NewsListViewModel
import com.example.e_progression.presentation.ui.theme.gradientColor1
import com.example.e_progression.presentation.ui.theme.gradientColor2
import com.example.e_progression.presentation.ui.theme.gray

@Composable
fun NewsScreen(navController: NavController, viewModel: NewsListViewModel = hiltViewModel()) {
    val state = viewModel.state.value
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
            .padding(5.dp)
    ) {
        if(state.news !=null) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 100.dp)
                .fillMaxSize()
//                .background(TextWhite)
        ) {
            if (state.news!=null){
            LazyColumn(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(gray)
                            .fillMaxWidth()
                    ) {
                            Column(modifier = Modifier.padding(8.dp).align(Alignment.CenterStart)) {
                                Text(
                                    text = "All News",
                                    style = MaterialTheme.typography.h1,
                                    color = Color.White
                                )
                                Text(
                                    text = "Count :${state.news.size}",
                                    style = MaterialTheme.typography.h2,
                                    color = Color.White
                                    )
                            }
                            Icon(
                                painter = painterResource(id = R.drawable.news),
                                contentDescription = "News Icon",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(80.dp)
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 16.dp)

                            )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                    items(state.news) { n ->
                        NewsListItem(news = n, onItemClick = {
                            navController.navigate(Screen.NewsItemScreen.route+"/${n.id}")
                        })
                    }
            } }
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