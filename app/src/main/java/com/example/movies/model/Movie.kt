package com.example.movies.model

import android.graphics.Bitmap

data class Movie(
    val id: Int = 0,
    val title: String = "Default",
    val description: String = "No description",
    var bmp: Bitmap? = null
)