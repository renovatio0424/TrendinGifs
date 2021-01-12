package com.j2rk.trendingifs.data

import com.j2rk.trendingifs.data.db.LocalDataSource
import com.j2rk.trendingifs.data.db.model.GiphyGif
import com.j2rk.trendingifs.data.network.RemoteDataSource
import com.j2rk.trendingifs.data.network.model.ResponseTrendingGifs
import io.reactivex.Single

interface GiphyRepository {
    fun loadGiphyGifList(): Single<List<GiphyGif>>
    fun loadGiphyGifListBy(pageIdx: Int): Single<List<GiphyGif>>
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

    override fun loadGiphyGifListBy(pageIdx: Int): Single<List<GiphyGif>> {
        return loadGiphyGifListBy(pageIdx)
            .flatMap {
                if (it.isEmpty()) {
                    remoteDataSource.loadGiphyGifListBy(pageIdx)
                        .flatMapCompletable { response -> localDataSource.insertAll(response.toGiphyGifList()) }
                        .andThen(localDataSource.loadAllBy(pageIdx))
                } else {
                    Single.just(it)
                }
            }
    }

    private fun ResponseTrendingGifs.toGiphyGifList(): List<GiphyGif> {
        return this.data.map {
            GiphyGif(
                remoteId = it.id,
                thumbnailUrl = it.images.fixed_width.url,
                pageIndex = this.pagination.offset
            )
        }
    }
}