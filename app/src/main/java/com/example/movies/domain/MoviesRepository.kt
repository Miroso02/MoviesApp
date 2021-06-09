package com.example.movies.domain

import com.example.movies.retrofitStuff.MoviesDatasource
import com.example.movies.retrofitStuff.MyMoviesDatasource

class MoviesRepository() {
    private val remoteDatasource: MoviesDatasource = MyMoviesDatasource()

    suspend fun getMovies(page: Int, searchPrompt: String?) =
        remoteDatasource.getMovies(page, searchPrompt)
    suspend fun getDetailedMovie(id: Int) =
        remoteDatasource.getDetailedMovie(id)
    suspend fun getMoviePoster(imagePath: String?) =
        remoteDatasource.getMoviePoster(imagePath ?: "")
}