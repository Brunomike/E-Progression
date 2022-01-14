package com.example.e_progression.data.remote.dto

import com.example.e_progression.domain.model.Fees

data class FeesDto(
    val Student: Student,
    val balance: String,
    val createdAt: String,
    val credit: String,
    val debit: String,
    val id: Int,
    val payment_description: String,
    val student_id: Int,
    val transaction_date: String,
    val updatedAt: String
)

fun FeesDto.toFees(): Fees {
    return Fees(
        paymentDescription = payment_description,
        transactionDate = transaction_date,
        debit = debit.toDouble(),
        credit = credit.toDouble(),
        balance = balance.toDouble(),
        studentAdmNo = student_id
    )
}