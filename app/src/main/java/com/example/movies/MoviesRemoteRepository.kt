package com.example.movies

import com.example.movies.retrofitModel.Movie

interface MoviesRemoteRepository {
    suspend fun getMoviesList(): List<Movie>
    suspend fun getDetailedMovie(id: Int): Movie
    suspend fun searchMovies(searchPrompt: String): List<Movie>
}