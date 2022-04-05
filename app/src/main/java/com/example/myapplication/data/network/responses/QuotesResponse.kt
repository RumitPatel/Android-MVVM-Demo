package com.example.myapplication.data.network.responses

import com.example.myapplication.data.db.entities.Quote

data class QuotesResponse(
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)