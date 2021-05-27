package com.example.movies

import android.util.Log
import com.example.movies.retrofitModel.MoviesList
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "3d86f62ce157e41ebdf62a5169f264b6"

var service: MyService = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/")
    .addConverterFactory(GsonConverterFactory.create())
    .build().create()
fun getData() {
    service.getMoviesList(2).enqueue(object : Callback<MoviesList> {
        override fun onResponse(call: Call<MoviesList>, response: Response<MoviesList>) {
            val body = response.body() ?: return
            for (movie in body.results) {
                Log.e(null, movie.title)
            }
        }
        override fun onFailure(call: Call<MoviesList>, t: Throwable) {
            Log.e(null, "something got wrong")
        }
    })
}

interface MyService {
    @GET("trending/movie/week?api_key=$API_KEY")
    fun getMoviesList(@Query("page") page: Int): Call<MoviesList>
}