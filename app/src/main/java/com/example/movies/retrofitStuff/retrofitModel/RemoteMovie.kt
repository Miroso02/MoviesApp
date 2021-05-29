package com.example.movies.retrofitStuff.retrofitModel

data class RemoteMovie (
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?
)