package com.example.movies.dbStuff

interface DBMoviesDatasource {
    fun getTrending(page: Int): Result<List<DBMovie>>
    fun saveTrending(movies: List<DBMovie>)
    fun search(request: String, page: Int): Result<List<DBMovie>>
}