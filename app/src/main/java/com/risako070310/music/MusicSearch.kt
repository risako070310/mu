package com.risako070310.music

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MusicSearch {
    @GET("v1/search")
    suspend fun searchMusic(
        @Header("Authorization") auth: String,
        @Query("q") q: String,
        @Query("type") type: String,
        @Query("limit") limit: Int,
    ): Data
}