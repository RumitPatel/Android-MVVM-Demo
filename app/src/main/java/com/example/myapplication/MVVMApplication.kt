package com.example.myapplication

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.network.HeaderInterceptor
import com.example.myapplication.data.network.MyApi
import com.example.myapplication.data.network.NetworkConnectionInterceptor
import com.example.myapplication.data.repository.QuotesRepository
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.ui.auth.AuthViewModelFactory
import com.example.myapplication.ui.home.profile.ProfileViewModelFactory
import com.example.myapplication.ui.home.quotes.QuotesViewModelFactory
import net.simplifiedcoding.mvvmsampleapp.data.preferences.PreferenceProvider
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {
    @RequiresApi(Build.VERSION_CODES.O)
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))
        bind() from singleton { HttpLoggingInterceptor() }
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { HeaderInterceptor(instance()) }
        bind() from singleton { MyApi(instance(),instance(), instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { QuotesRepository(instance(), instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { QuotesViewModelFactory(instance()) }
    }
}