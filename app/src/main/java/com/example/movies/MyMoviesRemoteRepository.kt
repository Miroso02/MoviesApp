package com.example.movies

import com.example.movies.retrofitModel.Movie
import com.example.movies.retrofitModel.MoviesList
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MyMoviesRemoteRepository() : MoviesRemoteRepository {
    private val service: MoviesAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create()
    }

    override suspend fun getMoviesList(): List<Movie> {
        return suspendCoroutine { continuation ->
            service.getMoviesList(1).enqueue(object : Callback<MoviesList> {
                override fun onResponse(call: Call<MoviesList>, response: Response<MoviesList>) {
                    continuation.resume(response.body()?.results ?: emptyList())
                }
                override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                    continuation.resumeWithException(Exception("something got wrong"))
                }
            })
        }
    }

    override suspend fun getDetailedMovie(id: Int): Movie {
        return Movie(-1, "", "", "")
    }

    override suspend fun searchMovies(searchPrompt: String): List<Movie> {
        return getMoviesList()
    }
}