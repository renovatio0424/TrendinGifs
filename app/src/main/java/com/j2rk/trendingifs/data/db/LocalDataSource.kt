package com.j2rk.trendingifs.data.db

import com.j2rk.trendingifs.data.db.model.GiphyGif
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

interface LocalDataSource {
    fun insertAll(gifList: List<GiphyGif>): Completable
    fun loadAll(): Single<List<GiphyGif>>
    fun loadAllBy(pageIdx: Int): Single<List<GiphyGif>>
    fun updateAll(gifList: List<GiphyGif>): Completable
    fun loadFavoriteGifList(): Single<List<GiphyGif>>
}

class LocalDataSourceImpl(private val database: AppDatabase) : LocalDataSource {
    private val scheduler: Scheduler
        get() = Schedulers.io()

    override fun insertAll(gifList: List<GiphyGif>): Completable {
        return database.giphyGifDao().insertAll(gifList).subscribeOn(scheduler)
    }

    override fun loadAll(): Single<List<GiphyGif>> {
        return database.giphyGifDao().loadAll().subscribeOn(scheduler)
    }

    override fun loadAllBy(pageIdx: Int): Single<List<GiphyGif>> {
        return database.giphyGifDao().loadAll(pageIdx).subscribeOn(scheduler)
    }

    override fun updateAll(gifList: List<GiphyGif>): Completable {
        return database.giphyGifDao().updateAll(gifList).subscribeOn(scheduler)
    }

    override fun loadFavoriteGifList(): Single<List<GiphyGif>> {
        return database.giphyGifDao().loadFavoriteGifList().subscribeOn(scheduler)
    }

}