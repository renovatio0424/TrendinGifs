package com.j2rk.trendingifs.data.network

import com.j2rk.trendingifs.data.network.model.ResponseTrendingGifs
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

interface RemoteDataSource {
    fun loadGiphyGifList(): Single<ResponseTrendingGifs>
    fun loadGiphyGifListBy(pageInx: Int): Single<ResponseTrendingGifs>
}

class HttpException(val code: Int, override val message: String) : Exception(message)

class RemoteDataSourceImpl(private val giphyService: GiphyApiService) : RemoteDataSource {
    private val scheduler: Scheduler
        get() = Schedulers.io()

    override fun loadGiphyGifList(): Single<ResponseTrendingGifs> {
        return giphyService.getTrendingGifs()
            .flatMap {
                if (it.meta.status != 200)
                    Single.error(HttpException(it.meta.status, it.meta.msg))
                else
                    Single.just(it)
            }.subscribeOn(scheduler)
    }

    override fun loadGiphyGifListBy(offset: Int): Single<ResponseTrendingGifs> {
        return giphyService.getTrendingGifs(offset = offset).flatMap {
            if (it.meta.status != 200)
                Single.error(HttpException(it.meta.status, it.meta.msg))
            else
                Single.just(it)
        }.subscribeOn(scheduler)
    }

}