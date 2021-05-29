package com.example.movies.retrofitStuff

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.movies.retrofitStuff.retrofitModel.MoviesList
import com.example.movies.retrofitStuff.retrofitModel.RemoteMovie
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MyMoviesRepository : MoviesRepository {
    private val moviesService: MoviesAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create()
    }
    override suspend fun getMoviesList(): List<RemoteMovie> {
        return suspendCoroutine { continuation ->
            moviesService.getMoviesList(1).enqueue(object : Callback<MoviesList> {
                override fun onResponse(call: Call<MoviesList>, response: Response<MoviesList>) {
                    continuation.resume(response.body()?.results ?: emptyList())
                }

                override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                    continuation.resumeWithException(Exception("something got wrong"))
                }
            })
        }
    }


    override suspend fun getDetailedMovie(id: Int): RemoteMovie =
        RemoteMovie(-1, "", "", "")

    override suspend fun searchMovies(searchPrompt: String): List<RemoteMovie> =
        getMoviesList()

    override suspend fun getMoviePoster(remoteMovie: RemoteMovie): Bitmap? {
        return suspendCoroutine { continuation ->
            moviesService.getMovieImage(remoteMovie.poster_path ?: "").enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val byteStream = response.body()?.byteStream()
                    val bmp = BitmapFactory.decodeStream(byteStream)
                    continuation.resume(bmp)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    continuation.resumeWithException(Exception("something got wrong"))
                }
            })
        }
    }
}