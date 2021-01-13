package com.j2rk.trendingifs.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.j2rk.trendingifs.data.GiphyRepository
import com.j2rk.trendingifs.data.db.model.GiphyGif
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val repository: GiphyRepository) : ViewModel() {
    private val _giphyGifList = MutableLiveData<List<GiphyGif>>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String>()

    val giphyGifList: LiveData<List<GiphyGif>> = _giphyGifList
    val isLoading: LiveData<Boolean> = _isLoading
    val errorMessage: LiveData<String> = _errorMessage

    private val disposable: CompositeDisposable = CompositeDisposable()

    init {
        initGiphyGifList()
    }

    private fun initGiphyGifList() {
        disposable.add(
            repository.loadGiphyGifList()
                .doOnSubscribe { _isLoading.value = true }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _giphyGifList.postValue(it)
                    _isLoading.postValue(false)
                }, {
                    it.printStackTrace()
                    _errorMessage.postValue(it.message)
                    _isLoading.postValue(false)
                })
        )
    }

    fun loadNextGiphyList(nextOffset: Int) {
        disposable.add(
            repository.loadGiphyGifListBy(nextOffset)
                .doOnSubscribe { _isLoading.value = true }
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    if (it.isEmpty())
                        Single.error(Exception("Empty List Result"))
                    else
                        Single.just(it)
                }
                .subscribe({
                    _isLoading.postValue(false)
                    _giphyGifList.postValue(it)
                }, {
                    _isLoading.postValue(false)
                    _errorMessage.postValue(it.message)
                })
        )
    }

    fun updateGiphy(giphyGif: GiphyGif) {
        disposable.add(
            repository.updateGiphyGifList(listOf(giphyGif))
                .subscribe()
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed)
            disposable.dispose()
    }
}