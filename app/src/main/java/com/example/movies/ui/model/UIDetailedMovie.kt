package com.example.movies.ui.model

import android.graphics.Bitmap

data class UIDetailedMovie(
    val title: String,
    val description: String,
    val releaseDate: String,
    val directors: String,
    val cast: String,
    val genres: String,
    var bmp: Bitmap? = null
)