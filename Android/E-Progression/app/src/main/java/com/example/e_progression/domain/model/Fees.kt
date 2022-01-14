package com.example.e_progression.domain.model

data class Fees(
    val paymentDescription:String,
    val transactionDate: String,
    val debit:Double,
    val credit:Double,
    val balance:Double,
    val studentAdmNo:Int
)