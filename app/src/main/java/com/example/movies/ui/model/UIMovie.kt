package com.example.movies.ui.model

import android.graphics.Bitmap

data class UIMovie(
    val id: Int = 0,
    val title: String = "Default",
    val description: String = "No description",
    var bmp: Bitmap? = null
)