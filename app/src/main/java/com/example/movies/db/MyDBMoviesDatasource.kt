package com.example.movies.db

import com.example.movies.db.model.DBDetailedMovie
import com.example.movies.db.model.DBMoviePoster
import com.example.movies.domain.model.DomainDetailedMovie
import com.example.movies.domain.model.DomainMovie
import com.example.movies.domain.MoviesDatasource
import java.io.InputStream

class MyDBMoviesDatasource : MoviesDatasource {
    private val dbImagesDAO get() = imagesDAO!!
    private val dbDetailedMoviesDAO get() = detailedMoviesDAO!!

    override suspend fun getMovies(page: Int, searchPrompt: String?): Result<List<DomainMovie>> =
        Result.failure(Exception("Database doesn't save movies list"))

    override suspend fun getMoviePoster(posterPath: String): Result<InputStream> =
        try {
            val poster = dbImagesDAO.getPoster(posterPath)
            if (poster != null)
                Result.success(poster.image.inputStream())
            else
                Result.failure(NullPointerException("no poster found"))
        } catch (e: Exception) {
            Result.failure(e)
        }

    suspend fun saveMoviePoster(posterPath: String, image: InputStream) {
        dbImagesDAO.savePoster(DBMoviePoster(posterPath, image.readBytes()))
    }

    override suspend fun getDetailedMovie(id: Int): Result<DomainDetailedMovie> =
        try {
            Result.success(dbDetailedMovieToDomainDetailedMovie(dbDetailedMoviesDAO.getDetailedMovie(id)))
        } catch (e: Exception) {
            Result.failure(e)
        }

    suspend fun saveDetailedMovie(movie: DomainDetailedMovie) {
        dbDetailedMoviesDAO.saveDetailedMovie(domainDetailedMovieToDBDetailedMovie(movie))
    }

    private fun dbDetailedMovieToDomainDetailedMovie(dbMovie: DBDetailedMovie): DomainDetailedMovie =
        with(dbMovie) { DomainDetailedMovie(id, title, description, releaseDate, directors, cast, genres, imagePath) }

    private fun domainDetailedMovieToDBDetailedMovie(domainMovie: DomainDetailedMovie): DBDetailedMovie =
        with(domainMovie) { DBDetailedMovie(id, title, description, releaseDate, directors, cast, genres, imagePath) }
}