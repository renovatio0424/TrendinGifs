package com.j2rk.trendingifs.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "giphy_gif")
data class GiphyGif(
    @PrimaryKey
    val id: String,
    val title: String,
    val thumbnailUrl: String,
    var isFavorite: Boolean = false,
    val offset: Int
)