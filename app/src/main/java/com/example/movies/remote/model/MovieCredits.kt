package com.example.movies.remote.model

data class MovieCredits(
    val cast: List<Actor>,
    val crew: List<Crew>
)