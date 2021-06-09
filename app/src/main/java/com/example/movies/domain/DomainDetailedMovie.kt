package com.example.movies.domain

data class DomainDetailedMovie(
    val title: String,
    val description: String,
    val releaseDate: String,
    val directors: String,
    val cast: String,
    val genres: String,
    var imagePath: String?
)