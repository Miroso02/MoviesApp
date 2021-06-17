package com.example.movies

import com.example.movies.db.DBMoviesDatasource
import com.example.movies.domain.DBCachingMoviesRepository
import com.example.movies.domain.MoviesDatasource
import com.example.movies.domain.MoviesLocalDatasource
import com.example.movies.domain.MoviesRepository
import com.example.movies.remote.RemoteMoviesDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {
    @Binds abstract fun repository(repository: DBCachingMoviesRepository): MoviesRepository
    @Binds abstract fun localDatasource(datasource: DBMoviesDatasource): MoviesLocalDatasource
    @Binds abstract fun remoteDatasource(datasource: RemoteMoviesDatasource): MoviesDatasource
}