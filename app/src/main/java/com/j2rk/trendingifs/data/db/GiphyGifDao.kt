package com.j2rk.trendingifs.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.j2rk.trendingifs.data.db.model.GiphyGif
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface GiphyGifDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(giphyGifList: List<GiphyGif>): Completable

    @Query("SELECT * FROM giphy_gif")
    fun loadAll(): Single<List<GiphyGif>>

    @Query("SELECT * FROM giphy_gif WHERE `offset` LIMIT 25 OFFSET :offset")
    fun loadAll(offset: Int): Single<List<GiphyGif>>

    @Query("SELECT * FROM giphy_gif WHERE isFavorite = 1")
    fun loadFavoriteGifList(): Single<List<GiphyGif>>

    @Update
    fun updateAll(giphyGifList: List<GiphyGif>): Completable

    @Delete
    fun delete(giphyGif: GiphyGif): Completable
}