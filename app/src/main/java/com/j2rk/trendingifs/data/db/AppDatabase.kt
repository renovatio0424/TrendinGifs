package com.j2rk.trendingifs.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.j2rk.trendingifs.data.db.model.GiphyGif

@Database(entities = [GiphyGif::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun giphyGifDao(): GiphyGifDao
}