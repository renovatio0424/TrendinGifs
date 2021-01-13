package com.j2rk.trendingifs.data.network

import com.j2rk.trendingifs.data.network.model.ResponseTrendingGifs
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

const val API_KEY_GIPHY = "eBRs5gkduPLn9phqKxkyaPw1SToJ9hIB"
const val BASE_URL_GIPHY = "https://api.giphy.com/"

/**
 * @link https://developers.giphy.com/docs/api/endpoint#trending
 *
 * @param api_key : Giphy API key
 * @param limit : The maximum number of objects to return. (Default: “25”)
 * @param offset : Specifies the starting position of the results. Defaults to 0.
 * @param rating : Filters results by specified rating. Acceptable values include g, pg, pg-13, r. If you do not specify a rating, you will receive results from all possible ratings.
 * */
interface GiphyApiService {
    @GET("v1/gifs/trending")
    fun getTrendingGifs(
        @Query("api_key") apiKey: String = API_KEY_GIPHY,
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0,
        @Query("rating") rating: String = ContentRating.G.toString().toLowerCase(Locale.ENGLISH)
    ): Single<ResponseTrendingGifs>
}

/**
 * @link https://developers.giphy.com/docs/optional-settings#rating
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
