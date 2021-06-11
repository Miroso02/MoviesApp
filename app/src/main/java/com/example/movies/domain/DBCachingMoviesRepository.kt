package com.example.movies.domain

import android.util.Log
import com.example.movies.domain.model.DomainDetailedMovie
import com.example.movies.domain.model.DomainMovie
import java.io.InputStream

class DBCachingMoviesRepository(
    private val remoteDatasource: MoviesDatasource,
    private val dbDatasource: MoviesLocalDatasource
) : MoviesRepository {
    override suspend fun getMovies(page: Int, searchPrompt: String?): Result<List<DomainMovie>> =
        remoteDatasource.getMovies(page, searchPrompt)

    override suspend fun getDetailedMovie(id: Int): Result<DomainDetailedMovie> {
        val dbResult = dbDatasource.getDetailedMovie(id)
        if (dbResult.isSuccess)
            return dbResult

        val remoteResult = remoteDatasource.getDetailedMovie(id)
        if (remoteResult.isSuccess)
            dbDatasource.saveDetailedMovie(remoteResult.getOrThrow())
        return remoteResult
    }

    override suspend fun getMoviePoster(imagePath: String?): Result<InputStream> {
        val dbResult = dbDatasource.getMoviePoster(imagePath ?: "")
        if (dbResult.isSuccess)
            return dbResult
        Log.v(null, "database failed")
        val remoteResult = remoteDatasource.getMoviePoster(imagePath ?: "")
        if (remoteResult.isSuccess) {
            val stream = remoteResult.getOrThrow()
            dbDatasource.saveMoviePoster(imagePath!!, stream)
            return dbDatasource.getMoviePoster(imagePath)
        }
        return remoteResult
    }
}