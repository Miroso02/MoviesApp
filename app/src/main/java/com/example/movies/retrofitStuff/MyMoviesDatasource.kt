package com.example.movies.retrofitStuff

import com.example.movies.domain.DomainDetailedMovie
import com.example.movies.domain.DomainMovie
import com.example.movies.retrofitStuff.retrofitModel.RemoteMovie
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.InputStream

class MyMoviesDatasource : MoviesDatasource {
    private val moviesService: MoviesAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create()
    }

    override suspend fun getMovies(page: Int, searchPrompt: String?): Result<List<DomainMovie>> =
        try {
            if (searchPrompt == null)
                Result.success(moviesService.getMoviesList(page).results.map(this::remoteMovieToDomainMovie))
            else
                Result.success(moviesService.searchMovies(page, searchPrompt).results.map(this::remoteMovieToDomainMovie))
        } catch (e: HttpException) {
            Result.failure(e)
        }

    override suspend fun getDetailedMovie(id: Int): Result<DomainDetailedMovie> =
        try {
            val remoteDetailedMovie = moviesService.getDetailedMovie(id)
            val movieCredits = moviesService.getMovieCredits(id)
            val detailedMovie = DomainDetailedMovie(
                remoteDetailedMovie.title,
                remoteDetailedMovie.overview,
                remoteDetailedMovie.releaseDate,
                movieCredits.crew
                    .filter { it.job == "Director" }
                    .fold("") { acc, director -> "$acc, ${director.name}" }
                    .drop(2),
                movieCredits.cast
                    .take(5)
                    .fold("") { acc, actor -> "$acc, ${actor.name}" }
                    .drop(2),
                remoteDetailedMovie.genres
                    .fold("") { acc, genre -> "$acc, ${genre.name}" }
                    .drop(2),
                remoteDetailedMovie.posterPath
            )
            Result.success(detailedMovie)
        } catch (e: HttpException) {
            Result.failure(e)
        }

    override suspend fun getMoviePoster(posterPath: String): Result<InputStream> =
        try {
            Result.success(moviesService.getMovieImage(posterPath).byteStream())
        } catch (e: HttpException) {
            Result.failure(e)
        }

    private fun remoteMovieToDomainMovie(remoteMovie: RemoteMovie): DomainMovie =
        with(remoteMovie) { DomainMovie(id, title, overview, poster_path) }
}