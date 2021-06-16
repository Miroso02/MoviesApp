package com.example.movies.domain

import com.example.movies.domain.model.DomainDetailedMovie
import com.example.movies.domain.model.DomainMovie
import dagger.Binds
import java.io.InputStream

interface MoviesRepository {
    suspend fun getMovies(page: Int, searchPrompt: String?): Result<List<DomainMovie>>
    suspend fun getDetailedMovie(id: Int): Result<DomainDetailedMovie>
    suspend fun getMoviePoster(imagePath: String?): Result<InputStream>
}