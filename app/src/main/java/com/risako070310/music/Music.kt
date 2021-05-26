package com.risako070310.music

import com.google.gson.annotations.SerializedName

data class Music(
    val title: String,
    @SerializedName("artistName") val artist: String,
    @SerializedName("thumbnailUrl") val imageURL: String
)