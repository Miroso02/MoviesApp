package com.example.movies.model

import android.graphics.Bitmap

data class DetailedMovie(
    val title: String = "",
    val description: String = "",
    val releaseDate: String = "",
    val directors: String = "",
    val cast: String = "",
    val genres: String = "",
    var bmp: Bitmap? = null
) {
    constructor(movie: DetailedMovie) : this(
        movie.title,
        movie.description,
        movie.releaseDate,
        movie.directors,
        movie.cast,
        movie.genres,
        movie.bmp
    )
}