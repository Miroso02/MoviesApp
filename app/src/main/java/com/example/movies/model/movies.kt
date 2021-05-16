package com.example.movies.model

val movies = listOf(
    Movie().apply {
        title = "It"
        description = "clown got insane"
        releaseDate = "sth in 2000s, I guess"
        directors = "someone"
        cast = "idk"
        genres = "horror"
    },
    Movie().apply {
        title = "Interstellar"
        description = "Some sci-fi shit"
        releaseDate = "sth in 2010s, I guess"
        directors = "my boi Nolan"
        cast = "idk too"
        genres = "sci-fi"
    },
    Movie().apply {
        title = "asdf"
        description = "asdf"
        releaseDate = "asdf"
        directors = "asdf"
        cast = "asdf"
        genres = "asdf"
    },
    Movie().apply {
        title = "asdf1"
        description = "asdf1"
        releaseDate = "asdf1"
        directors = "asdf1"
        cast = "asdf1"
        genres = "asdf1"
    },
    Movie().apply {
        title = "asdf2"
        description = "asdf2"
        releaseDate = "asdf2"
        directors = "asdf2"
        cast = "asdf2"
        genres = "asdf2"
    },
    Movie().apply {
        title = "asdf"
        description = "asdf"
        releaseDate = "asdf"
        directors = "asdf"
        cast = "asdf"
        genres = "asdf"
    },
    Movie().apply {
        title = "asdf1"
        description = "asdf1"
        releaseDate = "asdf1"
        directors = "asdf1"
        cast = "asdf1"
        genres = "asdf1"
    },
    Movie().apply {
        title = "asdf2"
        description = "asdf2"
        releaseDate = "asdf2"
        directors = "asdf2"
        cast = "asdf2"
        genres = "asdf2"
    },
    Movie().apply {
        title = "asdf"
        description = "asdf"
        releaseDate = "asdf"
        directors = "asdf"
        cast = "asdf"
        genres = "asdf"
    },
    Movie().apply {
        title = "asdf1"
        description = "asdf1"
        releaseDate = "asdf1"
        directors = "asdf1"
        cast = "asdf1"
        genres = "asdf1"
    },
    Movie().apply {
        title = "asdf2"
        description = "asdf2"
        releaseDate = "asdf2"
        directors = "asdf2"
        cast = "asdf2"
        genres = "asdf2"
    }
)
    .onEachIndexed { i, m -> m.id = i }