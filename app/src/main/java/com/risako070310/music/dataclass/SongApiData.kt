package com.risako070310.music.dataclass

import com.google.gson.annotations.SerializedName

data class SongData(
    @SerializedName("album") val album: SongAlbum,
    val name: String,
    @SerializedName("external_urls") val link: Links
)

data class SongAlbum(
    val artists: List<Artist>,
    val images: List<Images>
)

data class Links(
    @SerializedName("spotify") val songURL: String
)
