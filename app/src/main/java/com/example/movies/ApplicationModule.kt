package com.example.movies

import android.content.Context
import com.example.movies.db.DBMoviesDatasource
import com.example.movies.domain.DBCachingMoviesRepository
import com.example.movies.domain.MoviesDatasource
import com.example.movies.domain.MoviesLocalDatasource
import com.example.movies.domain.MoviesRepository
import com.example.movies.remote.RemoteMoviesDatasource
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val context: Context) {
    @Provides fun localDatasource(): MoviesLocalDatasource =
        DBMoviesDatasource(context)
}

@Module
abstract class ApplicationModule2 {
    @Binds abstract fun repository(repository: DBCachingMoviesRepository): MoviesRepository
    @Binds abstract fun remoteDatasource(datasource: RemoteMoviesDatasource): MoviesDatasource
}