package com.example.lecano

import androidx.annotation.ColorRes

data class Mascota(
    val id: Int,
    val nombre: String,
    val emoji: String,
    @ColorRes val colorRes: Int,
    var rating: Int,
    var ratedByUser: Boolean = false
)
