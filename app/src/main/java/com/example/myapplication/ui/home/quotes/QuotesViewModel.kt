package com.example.myapplication.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repository.QuotesRepository
import com.example.myapplication.util.lazyDeferred

class QuotesViewModel(
    repository: QuotesRepository
) : ViewModel() {

    val quotes by lazyDeferred {
        repository.getQuotes()
    }
}