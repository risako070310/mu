package com.risako070310.music

import java.time.LocalDateTime

data class User(
    val name: String,
    val song: String,
    val artist: String,
    val imageUrl: String,
    val comment: String,
    val update: LocalDateTime
)