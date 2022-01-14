package com.example.e_progression.presentation.main.results

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_progression.data.remote.dto.Student
import com.example.e_progression.domain.model.Exam
import com.example.e_progression.domain.model.Results
import com.example.e_progression.domain.model.Subject
import kotlin.math.roundToInt

@Composable
fun ResultScreeItem(type: Exam, results: List<Results>, student: Student) {
    val subjectsFromDB = arrayListOf<Subject>()
    results.forEach { result ->
        subjectsFromDB.add(
            Subject(
                result.subject_name,
                result.marks,
                result.grade,
                result.examType,
                result.student_id
            )
        )
    }


    val studentSubjects = arrayListOf<Subject>()
    val optionalSubjects = arrayListOf<Int>()

    var studentPoints = 0
    subjectsFromDB.forEach { subject ->
        if (subject.examType == type.exam_name) {
            studentSubjects.add(
                Subject(
                    subject.name,
                    subject.marks,
                    subject.grade,
                    subject.examType,
                    subject.admNo
                )
            )
        }
    }
    studentSubjects.forEach { result ->
        if (student.adm_no == result.admNo) {
            if (result.name == "Mathematics") {
                studentPoints += checkPoints(result.grade)
            }
            if (result.name == "English") {
                studentPoints += checkPoints(result.grade)
            }
            if (result.name == "Kiswahili") {
                studentPoints += checkPoints(result.grade)
            }
            if (result.name == "Chemistry") {
                studentPoints += checkPoints(result.grade)
            }
            if (result.name == "Biology") {
                studentPoints += checkPoints(result.grade)
            }
            if (result.name != "Mathematics" && result.name != "English" && result.name != "Kiswahili" && result.name != "Chemistry" && result.name != "Biology") {
                optionalSubjects.add(checkPoints(result.grade))
            }
        }
    }


    if (optionalSubjects.size > 0) {
        var high: Int = optionalSubjects[0]
        var higher: Int = optionalSubjects[0]
        var highest: Int = optionalSubjects[0]
        optionalSubjects.forEach { subject ->
            if (subject > highest) {
                highest = subject
            }
            if (subject < high) {
                high = subject
            }
        }
        optionalSubjects.forEach { subject ->
            if (subject in (high + 1) until highest) {
                higher = subject
            }
        }
        studentPoints += highest
        studentPoints += higher

    }
    val grade = checkGrade(studentPoints)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)
            .fillMaxWidth(.95f)
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(8.dp).weight(1f)
            ) {

                studentSubjects.forEach { subject ->
                    if (subject.admNo == student.adm_no) {
                        SubjectItem(subject, examType = type.exam_name)
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
            ) {
                if (studentPoints == 0) {
                    Column(modifier = Modifier.offset(x = 15.dp)) {
                        Text(
                            text = "Not Processed",
                            style = MaterialTheme.typography.body1,
                            color = Color.Black
                        )
                    }

                }else{
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = grade,
                            style = MaterialTheme.typography.h1,
                            fontSize = 50.sp,
                            color = Color.Black,
                        )
                        Text(
                            text = "Mean Grade",
                            style = MaterialTheme.typography.body1,
                            color = Color.Black,
                            maxLines = 1
                        )
                    }
                }

            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

fun checkGrade(points: Int): String {
    var grade = ""
    val average = (points / 7).toFloat().roundToInt()
    when (average) {
        12 -> {
            grade = "A"
        }
        11 -> {
            grade = "A-"
        }
        10 -> {
            grade = "B+"
        }
        9 -> {
            grade = "B"
        }
        8 -> {
            grade = "B-"
        }
        7 -> {
            grade = "C+"
        }
        6 -> {
            grade = "C"
        }
        5 -> {
            grade = "C-"
        }
        4 -> {
            grade = "D+"
        }
        3 -> {
            grade = "D"
        }
        2 -> {
            grade = "D-"
        }
        1 -> {
            grade = "E"
        }
    }
    return grade
}

fun checkPoints(grade: String): Int {
    var points: Int = 0
    if (grade == "A") {
        points = 12
    } else if (grade == "A-") {
        points = 11
    } else if (grade == "B+") {
        points = 10
    } else if (grade == "B") {
        points = 9
    } else if (grade == "B-") {
        points = 8
    } else if (grade == "C+") {
        points = 7
    } else if (grade == "C") {
        points = 6
    } else if (grade == "C-") {
        points = 5
    } else if (grade == "D+") {
        points = 4
    } else if (grade == "D") {
        points = 3
    } else if (grade == "D-") {
        points = 2
    } else if (grade == "E") {
        points = 1
    }
    return points
}