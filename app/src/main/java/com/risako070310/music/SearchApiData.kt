package com.risako070310.music

import com.google.gson.annotations.SerializedName


data class Data(
    @SerializedName("tracks") val trackData: Tracks
)

data class Tracks(
    val items: List<Items>
)

data class Items(
    val album: Album
)

data class Album(
    @SerializedName("artists") val artist: List<Artist>,
    @SerializedName("images") val images: List<Images>,
    val name: String,
    val id: String,
    @SerializedName("href") val songURL: String
)

data class Artist(
    val name: String
)

data class Images(
    @SerializedName("url") val imageUrl: String
)
