package com.example.e_progression.presentation.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.e_progression.R

@Composable
fun FeesSection(color:Color,paid:Double,balance:Double){
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
                text = "Fee Payment History",
                style = MaterialTheme.typography.h2,

                )
            Text(
                text = "Paid : Ksh.${paid}",
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
            Text(
                text = "Balance : Ksh.${balance}",
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(50.dp)

        ) {
            Icon(
                painter = painterResource(id = R.drawable.money),
                contentDescription = "View Fees",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }

    }
}