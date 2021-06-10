package com.example.movies.domain

import com.example.movies.domain.model.DomainDetailedMovie
import com.example.movies.domain.model.DomainMovie
import java.io.InputStream

interface MoviesLocalDatasource : MoviesDatasource {
    suspend fun saveMovies(movies: List<DomainMovie>)
    suspend fun saveMoviePoster(posterPath: String, image: InputStream)
    suspend fun saveDetailedMovie(movie: DomainDetailedMovie)
}