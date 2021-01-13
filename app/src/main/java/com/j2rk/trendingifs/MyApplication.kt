package com.j2rk.trendingifs

import androidx.multidex.MultiDexApplication
import androidx.room.Room
import com.j2rk.trendingifs.data.GiphyRepository
import com.j2rk.trendingifs.data.GiphyRepositoryImpl
import com.j2rk.trendingifs.data.db.AppDatabase
import com.j2rk.trendingifs.data.db.LocalDataSource
import com.j2rk.trendingifs.data.db.LocalDataSourceImpl
import com.j2rk.trendingifs.data.network.BASE_URL_GIPHY
import com.j2rk.trendingifs.data.network.GiphyApiService
import com.j2rk.trendingifs.data.network.RemoteDataSource
import com.j2rk.trendingifs.data.network.RemoteDataSourceImpl
import com.j2rk.trendingifs.ui.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : MultiDexApplication() {
    private val appModule = module {
        single<GiphyRepository> { GiphyRepositoryImpl(get(), get()) }
        factory {
            Room.databaseBuilder(androidContext(), AppDatabase::class.java, "TrendingGifDb").build()
        }
        factory<GiphyApiService> {
            Retrofit.Builder()
                .baseUrl(BASE_URL_GIPHY)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GiphyApiService::class.java)
        }
        factory<LocalDataSource> { LocalDataSourceImpl(get()) }
        factory<RemoteDataSource> { RemoteDataSourceImpl(get()) }
        viewModel { MainViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}