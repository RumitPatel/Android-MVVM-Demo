package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.db.entities.Quote
import com.example.myapplication.data.network.MyApi
import com.example.myapplication.data.network.SafeAPIRequest
import com.example.myapplication.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.simplifiedcoding.mvvmsampleapp.data.preferences.PreferenceProvider

class QuotesRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeAPIRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes() {
        val lastSavedAt = prefs.getLastSavedAt()

        if (lastSavedAt == null) {
            try {
                val response = apiRequest { api.getQuotes() }
                quotes.postValue(response.quotes)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            prefs.saveLastSavedAt(System.currentTimeMillis().toString())
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }
}