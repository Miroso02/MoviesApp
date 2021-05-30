package com.example.movies.retrofitStuff

import android.graphics.Bitmap
import com.example.movies.retrofitStuff.retrofitModel.MovieCredits
import com.example.movies.retrofitStuff.retrofitModel.RemoteDetailedMovie
import com.example.movies.retrofitStuff.retrofitModel.RemoteMovie

interface MoviesRepository {
    suspend fun getMoviesList(): List<RemoteMovie>
    suspend fun getMoviePoster(posterPath: String): Bitmap?
    suspend fun getDetailedMovie(id: Int): RemoteDetailedMovie
    suspend fun getMovieCredits(id: Int): MovieCredits
    suspend fun searchMovies(searchPrompt: String): List<RemoteMovie>
}