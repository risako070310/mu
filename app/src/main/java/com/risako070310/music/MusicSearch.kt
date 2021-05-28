package com.risako070310.music

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface MusicSearch {
    @GET("/v1/search?q={string}&type=track%2Cartist&limit=5")
    @Headers("Authorization: BQBowZtVwhw3WxthAi6P4MmwCFuUihNARVQMr2ujakks8hXGc4JOBV7xCLqErwOJbZiaMJXRpU9TPAqKI5c","Content-Type: application/json")
    suspend fun searchMusic(@Path("string") string: String): List<Tracks>
}