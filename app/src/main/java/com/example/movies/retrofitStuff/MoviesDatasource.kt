package com.example.movies.retrofitStuff

import android.graphics.Bitmap
import com.example.movies.domain.DomainDetailedMovie
import com.example.movies.domain.DomainMovie
import com.example.movies.model.DetailedMovie
import com.example.movies.retrofitStuff.retrofitModel.MovieCredits
import com.example.movies.retrofitStuff.retrofitModel.RemoteDetailedMovie
import com.example.movies.retrofitStuff.retrofitModel.RemoteMovie
import java.io.InputStream

interface MoviesDatasource {
    suspend fun getMovies(page: Int, searchPrompt: String? = null): Result<List<DomainMovie>>
    suspend fun getMoviePoster(posterPath: String): Result<InputStream>
    suspend fun getDetailedMovie(id: Int): Result<DomainDetailedMovie>
}