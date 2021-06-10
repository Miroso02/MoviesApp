package com.example.movies.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteDetailedMovie(
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    val genres: List<Genre>,
    @SerializedName("release_date")
    val releaseDate: String
)