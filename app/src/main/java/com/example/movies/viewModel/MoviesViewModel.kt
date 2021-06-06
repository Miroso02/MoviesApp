package com.example.movies.viewModel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.model.DetailedMovie
import com.example.movies.model.Movie
import com.example.movies.retrofitStuff.MoviesDatasource
import com.example.movies.retrofitStuff.MyMoviesDatasource
import com.example.movies.retrofitStuff.retrofitModel.RemoteMovie
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {
    private val moviesDatasource: MoviesDatasource = MyMoviesDatasource
    var defaultImageBitmap: Bitmap? = null
    var currentPage = 0
    var moviesList: MutableLiveData<ArrayList<Movie>> = MutableLiveData(arrayListOf())
    private var selectedId = -1

    var changedMovie = MutableLiveData<Movie?>(null)
    val selectedMovie = MutableLiveData<DetailedMovie?>(null)

    fun getMovies(searchPrompt: String?) {
        viewModelScope.launch {
            val remoteList = moviesDatasource.getMovies(currentPage, searchPrompt).getOrDefault(emptyList())
            val list = remoteList.map(this@MoviesViewModel::remoteMovieToMovie)
            for (movie in list) {
                movie.bmp = defaultImageBitmap
            }
            if (currentPage == 1) {
                moviesList.value = ArrayList(list)
            } else {
                moviesList.value = ArrayList(moviesList.value).apply {
                    addAll(list)
                }
            }
            for (i in remoteList.indices)
                loadMovieImage(list[i], remoteList[i].poster_path ?: "")
        }
    }

    private suspend fun loadMovieImage(movie: Movie, posterPath: String) {
        movie.bmp = moviesDatasource.getMoviePoster(posterPath).getOrDefault(defaultImageBitmap)
        changedMovie.value = movie
    }

    fun setSelectedMovie(index: Int) {
        selectedId = moviesList.value!![index].id
    }

    fun getDetailedMovie() {
        viewModelScope.launch {
            val movie = moviesDatasource.getDetailedMovie(selectedId).getOrThrow()
            val credits = moviesDatasource.getMovieCredits(selectedId).getOrThrow()
            val detailedMovie = DetailedMovie(
                movie.title,
                movie.overview,
                movie.release_date,
                credits.crew
                    .filter { it.job == "Director" }
                    .fold("") { acc, director -> "$acc, ${director.name}" }
                    .drop(2),
                credits.cast
                    .take(5)
                    .fold("") { acc, actor -> "$acc, ${actor.name}" }
                    .drop(2),
                movie.genres
                    .fold("") { acc, genre -> "$acc, ${genre.name}" }
                    .drop(2)
            )
            selectedMovie.value = detailedMovie
            selectedMovie.value = selectedMovie.value!!.apply {
                bmp = moviesDatasource.getMoviePoster(movie.poster_path ?: "").getOrDefault(defaultImageBitmap)
            }
        }
    }

    private fun remoteMovieToMovie(remote: RemoteMovie) =
        with(remote) { Movie(id, title, overview) }
}