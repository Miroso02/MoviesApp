package com.example.movies.retrofitStuff

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.movies.retrofitStuff.retrofitModel.MovieCredits
import com.example.movies.retrofitStuff.retrofitModel.RemoteDetailedMovie
import com.example.movies.retrofitStuff.retrofitModel.RemoteMovie
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object MyMoviesRepository : MoviesRepository {
    private val moviesService: MoviesAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create()
    }

    override suspend fun getMoviesList(page: Int): Result<List<RemoteMovie>> {
        return try {
            Result.success(moviesService.getMoviesList(page).results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDetailedMovie(id: Int): RemoteDetailedMovie =
        moviesService.getDetailedMovie(id)

    override suspend fun getMovieCredits(id: Int): MovieCredits =
        moviesService.getMovieCredits(id)

    override suspend fun searchMovies(searchPrompt: String): List<RemoteMovie> =
        emptyList()

    override suspend fun getMoviePoster(posterPath: String): Bitmap =
        moviesService.getMovieImage(posterPath).let {
            BitmapFactory.decodeStream(it.byteStream())
        }
}