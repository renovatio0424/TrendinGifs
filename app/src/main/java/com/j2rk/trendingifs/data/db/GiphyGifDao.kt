package com.j2rk.trendingifs.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.j2rk.trendingifs.data.db.model.GiphyGif
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface GiphyGifDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(giphyGifList: List<GiphyGif>): Completable

    @Query("SELECT * FROM giphy_gif")
    fun loadAll(): Single<List<GiphyGif>>

    @Query("SELECT * FROM giphy_gif WHERE pageIndex = :pageIdx")
    fun loadAll(pageIdx: Int): Single<List<GiphyGif>>

    @Delete
    fun delete(giphyGif: GiphyGif): Completable
}