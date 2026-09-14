package com.example.moneytracker


data class Transaction (
    val id: Int,
    val title: String,
    val amount: Long,
    val type: String,
    val category: String,
    val date: Long,
)
