package com.example.movies

import com.example.movies.model.Movie

interface MoviesRepository {
    suspend fun getMoviesList(): List<Movie>
    suspend fun getDetailedMovie(id: Int): Movie
    suspend fun searchMovies(searchPrompt: String): List<Movie>
}