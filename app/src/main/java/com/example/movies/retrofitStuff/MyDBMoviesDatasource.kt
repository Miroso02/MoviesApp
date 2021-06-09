package com.example.movies.retrofitStuff

import android.content.Context
import androidx.room.Room
import com.example.movies.dbStuff.*
import com.example.movies.dbStuff.moviesDB
import java.lang.Exception

class MyDBMoviesDatasource(context: Context) : DBMoviesDatasource {
    private var moviesDAO: DBMovieDAO
    init {
        moviesDB = Room.databaseBuilder(context, MoviesDB::class.java, "movies-database").build()
        moviesDAO = moviesDB!!.moviesDao()
    }
    override fun getTrending(page: Int): Result<List<DBMovie>> =
        try {
            Result.success(moviesDAO.get20(page * 20))
        }
        catch (e: Exception) {
            Result.failure(e)
        }

    override fun saveTrending(movies: List<DBMovie>) {
        moviesDAO.insertMovies(movies)
    }

    override fun search(request: String, page: Int): Result<List<DBMovie>> {
        return Result.success(emptyList())
    }
}