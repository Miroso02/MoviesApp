package com.example.movies.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.MoviesRepository
import com.example.movies.MyMoviesRemoteRepository
import com.example.movies.MyMoviesRepository
import com.example.movies.model.Movie
import com.example.movies.model.movies
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class AllMoviesViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository = MyMoviesRepository(MyMoviesRemoteRepository())
    val selectedMovie = MutableLiveData<Movie?>(null)
    var moviesList: List<Movie> = emptyList()
    fun getMovies(callback: (List<Movie>) -> Unit) {
        viewModelScope.launch {
            delay(5000)
            val list = moviesRepository.getMoviesList()
            moviesList = list
            callback(list)
        }
    }
    fun setSelected(id: Int) {
        selectedMovie.value = getMovie(id)
    }
    fun getMovie(id: Int) = moviesList[id]
}