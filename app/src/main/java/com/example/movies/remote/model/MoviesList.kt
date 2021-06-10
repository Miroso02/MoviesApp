package com.example.movies.remote.model

data class MoviesList (
    val page: Int,
    val results: List<RemoteMovie>,
    val totalPages: Int,
    val totalResults: Int
)