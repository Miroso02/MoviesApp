package com.example.movies.domain.model

data class DomainMovie(
    val id: Int,
    val title: String,
    val description: String,
    var imagePath: String?
)