package com.example.movies.dbStuff

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DBMovie::class], version = 1)
abstract class MoviesDB : RoomDatabase() {
    abstract fun moviesDao(): DBMovieDAO
}

internal var moviesDB: MoviesDB? = null