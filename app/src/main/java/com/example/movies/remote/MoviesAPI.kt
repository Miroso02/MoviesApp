package com.example.movies.remote

import com.example.movies.remote.model.MovieCredits
import com.example.movies.remote.model.MoviesList
import com.example.movies.remote.model.RemoteDetailedMovie
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {
    companion object {
        const val API_KEY = "3d86f62ce157e41ebdf62a5169f264b6"
    }

    @GET("trending/movie/week?api_key=$API_KEY")
    suspend fun getMoviesList(@Query("page") page: Int): MoviesList

    @GET("movie/{id}?api_key=$API_KEY")
    suspend fun getDetailedMovie(@Path("id") id: Int): RemoteDetailedMovie

    @GET("movie/{id}/credits?api_key=$API_KEY")
    suspend fun getMovieCredits(@Path("id") id: Int): MovieCredits

    @GET("https://image.tmdb.org/t/p/w780/{imagePath}")
    suspend fun getMovieImage(@Path("imagePath") imagePath: String): ResponseBody

    @GET("search/movie?api_key=$API_KEY")
    suspend fun searchMovies(@Query("page") page: Int, @Query("query") query: String): MoviesList
}