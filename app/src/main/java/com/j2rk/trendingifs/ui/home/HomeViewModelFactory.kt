package com.j2rk.trendingifs.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import com.j2rk.trendingifs.data.db.AppDatabase


class HomeViewModelFactory(private val appDatabase: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(appDatabase) as T
    }
}