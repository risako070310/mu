package com.risako070310.music

import java.time.LocalDateTime

data class User(
    val name: String,
    val song: String,
    val artist: String,
    val comment: String,
    val imageURL: String,
    val songURL: String,
    val update: LocalDateTime
)