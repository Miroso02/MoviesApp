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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DBMoviePoster

        if (posterPath != other.posterPath) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = posterPath.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}