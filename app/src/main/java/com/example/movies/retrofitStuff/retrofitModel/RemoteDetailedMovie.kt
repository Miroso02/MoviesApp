package com.example.movies.retrofitStuff.retrofitModel

data class RemoteDetailedMovie(
    val title: String,
    val overview: String,
    val poster_path: String?,
    val genres: List<Genre>,
    val release_date: String
)