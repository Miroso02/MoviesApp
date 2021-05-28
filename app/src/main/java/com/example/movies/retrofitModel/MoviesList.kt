package com.example.movies.retrofitModel

data class MoviesList (
    val page: Int,
    val results: List<RemoteMovie>,
    val totalPages: Int,
    val totalResults: Int
)