package com.risako070310.music

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface MusicGet {
    @GET("v1/tracks/{trackId}")
    suspend fun getMusic(
        @Header("Authorization") auth: String,
        @Header("Accept-Language") lang:String,
        @Path("trackId") trackId: String,
    ): SongData
}