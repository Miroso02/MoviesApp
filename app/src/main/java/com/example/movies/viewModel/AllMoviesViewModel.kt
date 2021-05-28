package com.example.movies.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.MoviesRepository
import com.example.movies.MyMoviesRepository
import com.example.movies.model.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AllMoviesViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository = MyMoviesRepository()
    val selectedMovie = MutableLiveData<Movie?>(null)
    var moviesList: List<Movie> = emptyList()
    fun getMovies(callback: (List<Movie>) -> Unit) {
        viewModelScope.launch {
            delay(5000)
            val list = moviesRepository.getMoviesList()
            moviesList = list
            Log.v("movieVM", "loaded ${list.size} movies")
            callback(list)
        }
    }
    fun setSelected(id: Int) {
        selectedMovie.value = getMovie(id)
    }
    fun getMovie(id: Int) = moviesList[id]
}