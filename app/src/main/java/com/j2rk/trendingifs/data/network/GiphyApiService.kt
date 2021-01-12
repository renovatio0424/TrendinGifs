package com.j2rk.trendingifs.data.network

import com.j2rk.trendingifs.data.network.model.ResponseTrendingGifs
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

const val API_KEY_GIPHY = "eBRs5gkduPLn9phqKxkyaPw1SToJ9hIB"
const val BASE_URL_GIPHY = "https://api.giphy.com/"

interface GiphyApiService {
    @GET("v1/gifs/trending")
    fun getTrendingGifs(
        @Query("api_key") apiKey: String = API_KEY_GIPHY,
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0,
        @Query("rating") rating: String = ContentRating.G.toString().toLowerCase(Locale.ENGLISH),
    ): Single<ResponseTrendingGifs>
}

/**
 * reference : https://developers.giphy.com/docs/optional-settings#rating
 *
 * G: Contains images that are broadly accepted as appropriate and commonly witnessed by people in a public environment.
 * PG: Contains images that are commonly witnessed in a public environment, but not as broadly accepted as appropriate.
 * PG_13: Contains images that are typically not seen unless sought out, but still commonly witnessed.
 * R: Contains images that are typically not seen unless sought out and could be considered alarming if witnessed.
 * */
enum class ContentRating {
    G,
    PG,
    PG_13,
    R
}
