package com.risako070310.music

import retrofit2.http.GET
import retrofit2.http.Path

interface MusicService {
    @GET("links?url=spotify%3Atrack%3A{trackId}")
    suspend fun getMusic(@Path("trackId") trackId: String): Music
}