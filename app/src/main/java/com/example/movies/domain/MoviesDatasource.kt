package com.example.movies.domain

import com.example.movies.domain.model.DomainDetailedMovie
import com.example.movies.domain.model.DomainMovie
import java.io.InputStream

interface MoviesDatasource {
    suspend fun getMovies(page: Int, searchPrompt: String? = null): Result<List<DomainMovie>>
    suspend fun getMoviePoster(posterPath: String): Result<InputStream>
    suspend fun getDetailedMovie(id: Int): Result<DomainDetailedMovie>
}