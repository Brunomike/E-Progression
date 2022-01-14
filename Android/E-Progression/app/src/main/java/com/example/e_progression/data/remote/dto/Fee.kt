package com.example.e_progression.data.remote.dto

data class Fee(
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