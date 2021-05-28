package com.risako070310.music

import com.google.gson.annotations.SerializedName

data class Tracks(
    @SerializedName("items") val items: Items
)

data class Items(
    @SerializedName("albums") val albums: List<Album>
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