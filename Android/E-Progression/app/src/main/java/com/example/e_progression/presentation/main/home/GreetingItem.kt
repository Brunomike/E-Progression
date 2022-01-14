package com.example.e_progression.presentation.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GreetingItem(){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .background(Color.White)
            .padding(5.dp)

    ){
        Column(modifier=Modifier.padding(5.dp)) {
            Text(text = "Good Morning Michael", style = MaterialTheme.typography.h1, color = Color.Black)
            Text(text = "We wish you have a nice day!", style = MaterialTheme.typography.h2, color = Color.Black)
        }
    }

}