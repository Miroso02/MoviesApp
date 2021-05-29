package com.example.movies.retrofitStuff

import android.graphics.Bitmap
import com.example.movies.retrofitStuff.retrofitModel.RemoteMovie

interface MoviesRepository {
    suspend fun getMoviesList(): List<RemoteMovie>
    suspend fun getMoviePoster(remoteMovie: RemoteMovie): Bitmap?
    suspend fun getDetailedMovie(id: Int): RemoteMovie
    suspend fun searchMovies(searchPrompt: String): List<RemoteMovie>
}