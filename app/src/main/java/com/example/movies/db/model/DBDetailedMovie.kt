package com.example.movies.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detailed_movies")
data class DBDetailedMovie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val releaseDate: String,
    val directors: String,
    val cast: String,
    val genres: String,
    val imagePath: String?
)