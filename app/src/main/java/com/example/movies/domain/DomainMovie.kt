package com.example.movies.domain

data class DomainMovie(
    val id: Int = 0,
    val title: String = "Default",
    val description: String = "No description",
    var imagePath: String?
)