package com.example.developerslife.data

import com.example.developerslife.data.model.GifItem
import com.example.developerslife.retrofit.GifListResponse
import com.example.developerslife.retrofit.GifService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GifRepository @Inject constructor(private val gifService: GifService) {

    suspend fun getRandomGif(): GifItem {
        return gifService.getRandomGif()
    }

    suspend fun getLatestGifs(): GifListResponse {
        return gifService.getLatestGifs(0)
    }

}