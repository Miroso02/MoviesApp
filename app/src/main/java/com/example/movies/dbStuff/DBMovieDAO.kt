package com.example.movies.dbStuff

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DBMovieDAO {
    @Query("select * from movies limit 20 offset (:offset)")
    fun get20(offset: Int): List<DBMovie>
    @Insert
    fun insertMovies(movies: List<DBMovie>)
}