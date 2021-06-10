package com.example.movies.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movies.db.model.DBMoviePoster

@Dao
interface DBMoviePosterDAO {
    @Insert
    suspend fun savePoster(dbMoviePoster: DBMoviePoster)
    @Query("select * from images where posterPath = (:posterPath)")
    suspend fun getPoster(posterPath: String): DBMoviePoster?
}