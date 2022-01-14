package com.example.e_progression.presentation.main.home.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun GreetingSection(name: String) {
    val c: Calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("h:mm a")
    val strDate: String = sdf.format(c.time)

    val dateArray: List<String> = strDate.split(" ")
    val dateSplit: List<String> = dateArray[0].split(":")
    val tod = dateArray[1]
    val hour = dateSplit[0].toInt()

    var message = ""

    Log.d("HOUR: ", hour.toString())
    Log.d("TOD: ", tod)

    when (tod) {
        "am" -> if (hour in 0..11) {
            message = "morning"
        } else if (hour == 12) {
            message = "morning"
        }
        "AM" -> if (hour in 0..11) {
            message = "morning"
        } else if (hour == 12) {
            message = "morning"
        }
        "pm" -> if (hour == 12) {
            message = "afternoon"
        } else if (hour in 1..3) {
            message = "afternoon"
        } else if (hour in 4..11) {
            message = "evening"
        }

        "PM" -> if (hour == 12) {
            message = "afternoon"
        } else if (hour in 1..3) {
            message = "afternoon"
        } else if (hour in 4..11) {
            message = "evening"
        }
    }

    Log.d("MESSAGE: ", message)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 15.dp)
        ) {
            Text(
                text = "Good $message, $name",
                style = MaterialTheme.typography.h2,
                color = Color.Black
            )
            Text(
                text = "We wish you have a good day!",
                style = MaterialTheme.typography.body1,
                color = Color.Black
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.greeting),
            contentDescription = "Greeting",
            tint = Color.Black,
            modifier = Modifier.size(90.dp)
        )
    }
}