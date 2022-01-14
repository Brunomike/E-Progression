package com.example.e_progression.presentation.main.results

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.e_progression.domain.model.Subject

@Composable
fun SubjectItem(subject: Subject, examType: String) {
    if (subject.examType == examType) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Box(modifier=Modifier.weight(1.2f)) {
                Text(
                    text = subject.name,
                    style = MaterialTheme.typography.body1,
                    color = Color.Black,
                )
            }
            Box(modifier=Modifier.weight(.5f)) {
                Text(
                    text = ":${subject.marks} ${subject.grade}",
                    style = MaterialTheme.typography.body1,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )
            }
        }
    }
}