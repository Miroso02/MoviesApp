package com.example.movies.db

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.room.Room
import com.example.movies.db.model.DBDetailedMovie
import com.example.movies.domain.model.DomainDetailedMovie
import com.example.movies.domain.model.DomainMovie
import com.example.movies.domain.MoviesLocalDatasource
import kotlinx.coroutines.*
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class DBMoviesDatasource @Inject constructor(context: Context) : MoviesLocalDatasource {
    private var moviesDB: MoviesDB = Room.databaseBuilder(context, MoviesDB::class.java, "movies-database")
        .fallbackToDestructiveMigration()
        .build()
    private var imagesDir: File
    private val detailedMoviesDAO = moviesDB.detailedMoviesDAO()
    private val contextWrapper = ContextWrapper(context)

    init {
        imagesDir = contextWrapper.getDir("images", Context.MODE_PRIVATE)
        if (!imagesDir.exists())
            imagesDir.mkdir()
    }

    override suspend fun getMovies(page: Int, searchPrompt: String?): Result<List<DomainMovie>> =
        Result.failure(Exception("Database doesn't save movies list"))

    override suspend fun saveMovies(movies: List<DomainMovie>) {}

    override suspend fun getMoviePoster(posterPath: String): Result<InputStream> =
        try {
            val posterPath0 = posterPath.drop(1)
            val file = File(imagesDir, posterPath0)
            if (!file.exists())
                Result.failure(NullPointerException("no poster found"))
            else
                withContext(Dispatchers.IO) {
                    Log.v(null, "loaded from file")
                    Result.success(contextWrapper.openFileInput(posterPath0))
                }
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun saveMoviePoster(posterPath: String, image: InputStream) {
        val posterPath0 = posterPath.drop(1)
        val file = File(imagesDir, posterPath0)
        withContext(Dispatchers.IO) {
            if (!file.exists()) {
                file.createNewFile()
                contextWrapper.openFileOutput(posterPath0, Context.MODE_PRIVATE).use {
                    it.write(image.readBytes())
                }
            }
        }
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