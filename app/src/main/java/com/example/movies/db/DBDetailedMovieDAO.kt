package com.example.movies.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movies.db.model.DBDetailedMovie

@Dao
interface DBDetailedMovieDAO {
    @Insert
    suspend fun saveDetailedMovie(movie: DBDetailedMovie)
    @Query("select * from detailed_movies where id = (:id)")
    suspend fun getDetailedMovie(id: Int): DBDetailedMovie
}