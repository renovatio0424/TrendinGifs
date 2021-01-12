package com.j2rk.trendingifs.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.j2rk.trendingifs.data.GiphyRepository
import com.j2rk.trendingifs.data.GiphyRepositoryImpl
import com.j2rk.trendingifs.data.db.AppDatabase
import com.j2rk.trendingifs.data.db.LocalDataSourceImpl
import com.j2rk.trendingifs.data.db.model.GiphyGif
import com.j2rk.trendingifs.data.network.BASE_URL_GIPHY
import com.j2rk.trendingifs.data.network.GiphyApiService
import com.j2rk.trendingifs.data.network.RemoteDataSourceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel(appDatabase: AppDatabase) : ViewModel() {
    private val repository: GiphyRepository

    private val _giphyGifList = MutableLiveData<List<GiphyGif>>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String>()

    val giphyGifList: LiveData<List<GiphyGif>> = _giphyGifList
    val isLoading: LiveData<Boolean> = _isLoading
    val errorMessage: LiveData<String> = _errorMessage

    private val disposable: CompositeDisposable

    init {
        val giphyApiService: GiphyApiService = Retrofit.Builder()
            .baseUrl(BASE_URL_GIPHY)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GiphyApiService::class.java)

        repository = GiphyRepositoryImpl(
            remoteDataSource = RemoteDataSourceImpl(giphyApiService),
            localDataSource = LocalDataSourceImpl(appDatabase)
        )

        disposable = CompositeDisposable()

        fetchGiphyGifList()
    }

    private fun fetchGiphyGifList() {
        disposable.add(
            repository.loadGiphyGifList()
                .doOnSubscribe { _isLoading.value = true }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("test", "load list : $it")
                    _giphyGifList.postValue(it)
                    _isLoading.postValue(false)
                }, {
                    Log.d("test", "load error : $it")
                    it.printStackTrace()
                    _errorMessage.postValue(it.message)
                    _isLoading.postValue(false)
                })
        )
    }

    fun loadNextGiphyList(nextPageIndex: Int) {
        disposable.add(
            repository.loadGiphyGifListBy(nextPageIndex)
                .doOnSubscribe { _isLoading.value = true }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _giphyGifList.postValue(it)
                    _isLoading.postValue(false)
                }, {
                    _errorMessage.postValue(it.message)
                    _isLoading.postValue(false)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed)
            disposable.dispose()
    }
}