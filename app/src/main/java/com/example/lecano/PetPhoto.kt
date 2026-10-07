package com.example.lecano

import androidx.annotation.ColorRes

data class PetPhoto(
    val emoji: String,
    @ColorRes val colorRes: Int,
    val rating: Int
)
