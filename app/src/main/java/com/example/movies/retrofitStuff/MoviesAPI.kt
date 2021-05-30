package com.example.movies.retrofitStuff

import com.example.movies.retrofitStuff.retrofitModel.MovieCredits
import com.example.movies.retrofitStuff.retrofitModel.MoviesList
import com.example.movies.retrofitStuff.retrofitModel.RemoteDetailedMovie
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {
    companion object {
        const val API_KEY = "3d86f62ce157e41ebdf62a5169f264b6"
    }

    @GET("trending/movie/week?api_key=$API_KEY")
    fun getMoviesList(@Query("page") page: Int): Call<MoviesList>

    @GET("movie/{id}?api_key=$API_KEY")
    fun getDetailedMovie(@Path("id") id: Int): Call<RemoteDetailedMovie>

    @GET("movie/{id}/credits?api_key=$API_KEY")
    fun getMovieCredits(@Path("id") id: Int): Call<MovieCredits>

    @GET("https://image.tmdb.org/t/p/w780/{imagePath}")
    fun getMovieImage(@Path("imagePath") imagePath: String): Call<ResponseBody>

}