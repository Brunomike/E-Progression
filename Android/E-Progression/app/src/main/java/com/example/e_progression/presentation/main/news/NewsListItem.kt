package com.example.e_progression.presentation.main.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.e_progression.domain.model.News
import com.example.e_progression.R

@Composable
fun NewsListItem(
    news: News,
    onItemClick: (News) -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .clip(RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(news) }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.news),
                contentDescription = "News Icon",
                modifier = Modifier
                    .padding(end = 25.dp)
                    .size(40.dp)
            )

            Column {
                Text(
                    text = "${news.newsTitle}",
                    style = MaterialTheme.typography.h2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black
                )
                Text(
                    text = news.newsSubTitle,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.body1,
                    color = Color.Black
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}