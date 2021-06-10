package com.example.movies

import android.content.Context
import com.example.movies.db.DBMoviesDatasource
import com.example.movies.domain.DBCachingMoviesRepository
import com.example.movies.domain.MoviesDatasource
import com.example.movies.domain.MoviesLocalDatasource
import com.example.movies.domain.MoviesRepository
import com.example.movies.remote.RemoteMoviesDatasource
import com.example.movies.ui.viewModel.MoviesViewModel

class AppContainer(context: Context) {
    private val localDatasource: MoviesLocalDatasource = DBMoviesDatasource(context)
    private val remoteDatasource: MoviesDatasource = RemoteMoviesDatasource()
    private val repository: MoviesRepository = DBCachingMoviesRepository(remoteDatasource, localDatasource)
    val moviesViewModel: MoviesViewModel = MoviesViewModel(repository)
}