package com.risako070310.music.dataclass

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("tracks") val trackData: Tracks
)

data class Tracks(
    val items: List<Items>
)

data class Items(
    val album: Album,
    val id: String,
    val name: String
)

data class Album(
    @SerializedName("artists") val artist: List<Artist>,
    @SerializedName("images") val images: List<Images>
)

data class Artist(
    val name: String
)

data class Images(
    @SerializedName("url") val imageUrl: String
)
