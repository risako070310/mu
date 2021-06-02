package com.risako070310.music

import com.google.gson.annotations.SerializedName

data class SongData(
    @SerializedName("album") val album: SongAlbum,
    val name: String,
    @SerializedName("linked_from") val link: Links
)

data class SongAlbum(
    val artists: List<Artist>,
    val images: List<Images>
)

data class Links(
    @SerializedName("href") val songURL: String,
    val id: String
)
