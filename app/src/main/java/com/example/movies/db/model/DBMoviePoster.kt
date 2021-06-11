package com.example.movies.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class DBMoviePoster(
    @PrimaryKey
    val posterPath: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray
)