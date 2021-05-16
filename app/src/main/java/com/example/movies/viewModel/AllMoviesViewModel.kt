package com.example.movies.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.model.Movie
import com.example.movies.model.movies

class AllMoviesViewModel : ViewModel() {
    val selectedMovie = MutableLiveData<Movie?>(null)
    val moviesList: LiveData<List<Movie>> by lazy {
        MutableLiveData(getMovies())
    }
    private fun getMovies() = movies
    fun setSelected(id: Int) {
        selectedMovie.value = getMovie(id)
    }
    fun getMovie(id: Int) = moviesList.value?.get(id)
}