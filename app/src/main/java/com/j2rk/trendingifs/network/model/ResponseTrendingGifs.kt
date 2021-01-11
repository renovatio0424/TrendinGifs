package com.j2rk.trendingifs.network.model

data class ResponseTrendingGifs(
    val `data`: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)