package com.example.movies

class MyMoviesRepository(private val repository: MoviesRemoteRepository) : MoviesRepository {
    override suspend fun getMoviesList(): List<com.example.movies.model.Movie> =
        repository.getMoviesList().map(this::getMovieFromRemote)

    override suspend fun getDetailedMovie(id: Int): com.example.movies.model.Movie =
        repository.getDetailedMovie(id).run(this::getMovieFromRemote)

    override suspend fun searchMovies(searchPrompt: String): List<com.example.movies.model.Movie> =
        repository.searchMovies(searchPrompt).map(this::getMovieFromRemote)

    private fun getMovieFromRemote(remoteMovie: com.example.movies.retrofitModel.Movie): com.example.movies.model.Movie {
        return com.example.movies.model.Movie().apply {
            id = remoteMovie.id
            title = remoteMovie.title
            description = remoteMovie.overview
        }
    }
}