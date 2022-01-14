package com.example.e_progression.presentation.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_progression.presentation.ui.theme.ButtonBlue
import com.example.e_progression.presentation.ui.theme.LightRed
import com.example.e_progression.presentation.ui.theme.TextWhite
import com.example.e_progression.R
import com.example.e_progression.domain.model.News
import com.example.e_progression.presentation.Screen

@Composable
fun LatestNewsSection(color: Color,newsItem: News,navController:NavController){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = "Latest News",
                style = MaterialTheme.typography.h2,

            )
            Text(
                text = "${newsItem.newsSubTitle}",
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.White)
                .padding(10.dp)
        ) {
            IconButton(onClick = {
                navController.navigate(Screen.NewsItemScreen.route+"/${newsItem.id}")
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.open),
                    contentDescription = "View News",
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }

        }

    }
}