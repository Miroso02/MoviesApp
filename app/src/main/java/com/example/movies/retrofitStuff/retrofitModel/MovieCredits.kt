package com.example.movies.retrofitStuff.retrofitModel

data class MovieCredits(
    val cast: List<Actor>,
    val crew: List<Crew>
)