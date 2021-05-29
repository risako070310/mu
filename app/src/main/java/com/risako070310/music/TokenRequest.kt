package com.risako070310.music

import retrofit2.http.*

interface TokenRequest {
    @FormUrlEncoded
    @POST("/token")
    @Headers("Authorization: Basic ZmRmNmY0YjExZjNmNDExYmE1ZDFlYjg1OWIxM2MxZjg6YmZkMDM4Nzg2NzYxNGJmMWI3NzQxOWFjNTkwYWZhZGM=")
    suspend fun getToken(@Field("grant_type") grant_type: String): String
}
