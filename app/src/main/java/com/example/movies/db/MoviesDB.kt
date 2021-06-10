package com.example.movies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.db.model.DBDetailedMovie
import com.example.movies.db.model.DBMoviePoster

@Database(entities = [DBDetailedMovie::class, DBMoviePoster::class], version = 3)
abstract class MoviesDB : RoomDatabase() {
    abstract fun imagesDao(): DBMoviePosterDAO
    abstract fun detailedMoviesDAO(): DBDetailedMovieDAO
}