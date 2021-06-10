package com.example.movies.db

import android.content.Context
import androidx.room.Room
import com.example.movies.db.model.DBDetailedMovie
import com.example.movies.db.model.DBMoviePoster
import com.example.movies.domain.model.DomainDetailedMovie
import com.example.movies.domain.model.DomainMovie
import com.example.movies.domain.MoviesLocalDatasource
import java.io.InputStream

class DBMoviesDatasource(context: Context) : MoviesLocalDatasource {
    private var moviesDB: MoviesDB = Room.databaseBuilder(context, MoviesDB::class.java, "movies-database")
        .fallbackToDestructiveMigration()
        .build()
    private val imagesDAO = moviesDB.imagesDao()
    private val detailedMoviesDAO = moviesDB.detailedMoviesDAO()

    override suspend fun getMovies(page: Int, searchPrompt: String?): Result<List<DomainMovie>> =
        Result.failure(Exception("Database doesn't save movies list"))

    override suspend fun getMoviePoster(posterPath: String): Result<InputStream> =
        try {
            val poster = imagesDAO.getPoster(posterPath)
            if (poster != null)
                Result.success(poster.image.inputStream())
            else
                Result.failure(NullPointerException("no poster found"))
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun saveMovies(movies: List<DomainMovie>) {}

    override suspend fun saveMoviePoster(posterPath: String, image: InputStream) {
        imagesDAO.savePoster(DBMoviePoster(posterPath, image.readBytes()))
    }

    override suspend fun getDetailedMovie(id: Int): Result<DomainDetailedMovie> =
        try {
            Result.success(dbDetailedMovieToDomainDetailedMovie(detailedMoviesDAO.getDetailedMovie(id)))
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun saveDetailedMovie(movie: DomainDetailedMovie) {
        detailedMoviesDAO.saveDetailedMovie(domainDetailedMovieToDBDetailedMovie(movie))
    }

    private fun dbDetailedMovieToDomainDetailedMovie(dbMovie: DBDetailedMovie): DomainDetailedMovie =
        with(dbMovie) { DomainDetailedMovie(id, title, description, releaseDate, directors, cast, genres, imagePath) }

    private fun domainDetailedMovieToDBDetailedMovie(domainMovie: DomainDetailedMovie): DBDetailedMovie =
        with(domainMovie) { DBDetailedMovie(id, title, description, releaseDate, directors, cast, genres, imagePath) }
}