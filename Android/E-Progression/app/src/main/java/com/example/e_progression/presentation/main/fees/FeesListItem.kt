package com.example.e_progression.presentation.main.fees

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_progression.R
import com.example.e_progression.common.convertDate
import com.example.e_progression.domain.model.Fees

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FeesListItem(fees: Fees) {
    var transactionType=""
    var title=""
    if (fees.credit ==0.0){
        transactionType="DR"
        title="Paid"
    }else{
        transactionType="CR"
        title="Balance"
    }
    Box(
        modifier = Modifier
            .background(Color.White)
            .clip(RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.money),
                contentDescription = "News Icon",
                modifier = Modifier.padding(end = 5.dp)
                    .weight(0.3f)
            )
            Column (modifier = Modifier.weight(3f)){
                Text(
                    text = fees.paymentDescription,
                    style = MaterialTheme.typography.h2,
                    color = Color.Black
                )
                Text(
                    text = "Transaction Date: ${convertDate(fees.transactionDate)}",
                    style = MaterialTheme.typography.body1,
                    color = Color.Black
                )
                Text(
                    text = "${title}:Ksh.${if (title=="Balance")fees.credit else fees.debit}",
                    style = MaterialTheme.typography.body1,
                    color = Color.Black
                )
            }

            Text(
                text = transactionType,
                style = MaterialTheme.typography.body1,
                color = if(title=="Balance")Color.Red else Color.Green,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 60.dp)
                    .weight(0.2f)
            )

        }
    }
    //Spacer(modifier = Modifier.height(10.dp))
}