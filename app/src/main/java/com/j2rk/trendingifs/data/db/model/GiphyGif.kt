package com.j2rk.trendingifs.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "giphy_gif")
data class GiphyGif(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val remoteId: String,
    val thumbnailUrl: String,
    var isFavorite: Boolean = false,
    val pageIndex: Int
)