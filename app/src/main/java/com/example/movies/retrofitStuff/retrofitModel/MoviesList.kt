package com.example.movies.retrofitStuff.retrofitModel

data class MoviesList (
    val page: Int,
    val results: List<RemoteMovie>,
    val totalPages: Int,
    val totalResults: Int
)