package com.example.movies.remote.model

data class RemoteMovie (
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?
)