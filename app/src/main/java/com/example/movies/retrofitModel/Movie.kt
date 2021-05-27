package com.example.movies.retrofitModel

data class Movie (
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?
)