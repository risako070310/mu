package com.risako070310.music

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MusicSearch {
    @GET("v1/search")
    @Headers(
        "Authorization: Bearer BQDXzXw32nWxdh01crIBq2MdMRPcBvWQgKOmc2JPPjQLOYdSXWEe6r_1VrmOXYyWGJ3PSR_gjYYYAnaaWJw",
        "Accept: application/json",
        "Content-Type: application/json"
    )
    suspend fun searchMusic(
        @Query("q") q: String,
        @Query("type") type: String,
        @Query("limit") limit: Int
    ): Data
}