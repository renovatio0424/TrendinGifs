package com.j2rk.trendingifs.network

import com.j2rk.trendingifs.network.model.ResponseTrendingGifs
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

const val API_KEY_GIPHY = "eBRs5gkduPLn9phqKxkyaPw1SToJ9hIB"
const val BASE_URL_GIPHY = "https://api.giphy.com/"

interface GiphyService {
    @GET("v1/gifs/trending")
    fun getTrendingGifs(
        @Query("api_key") apiKey: String = API_KEY_GIPHY,
        @Query("limit") limit: Int = 20,
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