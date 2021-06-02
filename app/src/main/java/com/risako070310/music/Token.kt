package com.risako070310.music

import com.google.gson.annotations.SerializedName

data class Token (
    @SerializedName("access_token") val token: String
)
