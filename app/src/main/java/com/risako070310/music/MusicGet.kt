package com.risako070310.music

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface MusicGet {
    @GET("/v1/tracks/{trackId}")
    @Headers("Authorization: BQBowZtVwhw3WxthAi6P4MmwCFuUihNARVQMr2ujakks8hXGc4JOBV7xCLqErwOJbZiaMJXRpU9TPAqKI5c")
    suspend fun getMusic(@Path("trackId") trackId: String): List<Tracks>
}