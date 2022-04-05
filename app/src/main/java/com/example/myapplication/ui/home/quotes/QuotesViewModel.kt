package com.example.myapplication.ui.home.quotes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repository.QuotesRepository
import com.example.myapplication.util.lazyDeferred

@RequiresApi(Build.VERSION_CODES.O)
class QuotesViewModel(
    repository: QuotesRepository
) : ViewModel() {

    val quotes by lazyDeferred {
        repository.getQuotes()
    }
}