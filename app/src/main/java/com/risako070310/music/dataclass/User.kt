package com.risako070310.music.dataclass

data class User(
    val name: String = "",
    val song: String = "",
    val artist: String = "",
    val comment: String = "",
    val imageURL: String = "",
    val songURL: String = "",
    val location: Int = 0,
    val locationSwitch: String = ""
)