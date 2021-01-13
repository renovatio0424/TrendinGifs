package com.j2rk.trendingifs.data

import com.j2rk.trendingifs.data.db.LocalDataSource
import com.j2rk.trendingifs.data.db.model.GiphyGif
import com.j2rk.trendingifs.data.network.RemoteDataSource
import com.j2rk.trendingifs.data.network.model.ResponseTrendingGifs
import io.reactivex.Completable
import io.reactivex.Single

interface GiphyRepository {
    fun loadGiphyGifList(): Single<List<GiphyGif>>
    fun loadGiphyGifListBy(nextOffset: Int): Single<List<GiphyGif>>
    fun updateGiphyGifList(giphyGifList: List<GiphyGif>): Completable
    fun loadFavoriteGifList(): Single<List<GiphyGif>>
}

class GiphyRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : GiphyRepository {
    override fun loadGiphyGifList(): Single<List<GiphyGif>> {
        return localDataSource.loadAll()
            .flatMap {
                if (it.isEmpty()) {
                    remoteDataSource.loadGiphyGifList()
                        .flatMapCompletable { response -> localDataSource.insertAll(response.toGiphyGifList()) }
                        .andThen(localDataSource.loadAll())
                } else {
                    Single.just(it)
                }
            }
    }

    override fun loadGiphyGifListBy(nextOffset: Int): Single<List<GiphyGif>> {
        return localDataSource.loadAllBy(nextOffset)
            .flatMap {
                if (it.isEmpty()) {
                    remoteDataSource.loadGiphyGifListBy(nextOffset)
                        .flatMapCompletable { response -> localDataSource.insertAll(response.toGiphyGifList()) }
                        .andThen(localDataSource.loadAllBy(nextOffset))
                } else {
                    Single.just(it)
                }
            }
    }

    override fun updateGiphyGifList(giphyGifList: List<GiphyGif>): Completable {
        return localDataSource.updateAll(giphyGifList)
    }

    override fun loadFavoriteGifList(): Single<List<GiphyGif>> {
        return localDataSource.loadFavoriteGifList()
    }

    private fun ResponseTrendingGifs.toGiphyGifList(): List<GiphyGif> {
        var offset = this.pagination.offset
        return this.data.map {
            GiphyGif(
                id = it.id,
                title = it.title,
                thumbnailUrl = it.images.fixed_width.url,
                offset = ++offset
            )
        }
    }
}