package com.example.movies.model

import android.graphics.Bitmap

data class DetailedMovie(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val releaseDate: String = "",
    val directors: String = "",
    val cast: String = "",
    val genres: String = "",
    var bmp: Bitmap? = null
)