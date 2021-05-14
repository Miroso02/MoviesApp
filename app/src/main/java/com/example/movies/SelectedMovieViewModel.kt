package com.example.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectedMovieViewModel: ViewModel() {
    val movie: MutableLiveData<Movie> by lazy {
        MutableLiveData(getMovie(0))
    }
    fun setMovie(id: Int) {
        movie.value = getMovie(id)
    }
    private fun getMovie(id: Int) = movies[id]
}