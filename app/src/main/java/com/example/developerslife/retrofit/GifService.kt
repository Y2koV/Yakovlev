package com.example.developerslife.retrofit

import com.example.developerslife.data.model.GifItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GifService {
    companion object {
        const val BASE_URL = "https://developerslife.ru/"
    }

    @GET("random")
    suspend fun getRandomGif(@Query("json") json: Boolean = true): GifItem

    @GET("latest/{page}")
    suspend fun getLatestGifs(
        @Path("page") page: Int,
        @Query("json") json: Boolean = true
    ): GifListResponse
}