package com.example.movies.domain.model

data class DomainDetailedMovie(
    val id: Int,
    val title: String,
    val description: String,
    val releaseDate: String,
    val directors: String,
    val cast: String,
    val genres: String,
    var imagePath: String?
)