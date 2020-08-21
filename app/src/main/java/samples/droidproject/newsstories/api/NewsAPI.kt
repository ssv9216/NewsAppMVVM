package samples.droidproject.newsstories.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import samples.droidproject.newsstories.models.NewsResponse
import samples.droidproject.newsstories.utils.Constants.Companion.API_KEY

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "in",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}