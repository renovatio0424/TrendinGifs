package com.j2rk.trendingifs.data.network

import com.j2rk.trendingifs.data.network.model.ResponseTrendingGifs
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

interface RemoteDataSource {
    fun loadGiphyGifList(): Single<ResponseTrendingGifs>
    fun loadGiphyGifListBy(pageInx: Int): Single<ResponseTrendingGifs>
}

class RemoteDataSourceImpl(private val giphyService: GiphyApiService) : RemoteDataSource {
    private val scheduler: Scheduler
        get() = Schedulers.io()

    override fun loadGiphyGifList(): Single<ResponseTrendingGifs> {
        return giphyService.getTrendingGifs().subscribeOn(scheduler)
    }

    override fun loadGiphyGifListBy(pageInx: Int): Single<ResponseTrendingGifs> {
        return giphyService.getTrendingGifs(offset = pageInx).subscribeOn(scheduler)
    }

}