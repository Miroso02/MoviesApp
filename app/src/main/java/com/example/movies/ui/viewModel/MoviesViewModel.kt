package com.example.movies.ui.viewModel

import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.MoviesRepository
import com.example.movies.domain.model.DomainDetailedMovie
import com.example.movies.domain.model.DomainMovie
import com.example.movies.ui.model.UIDetailedMovie
import com.example.movies.ui.model.UIMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {
    var currentPage = 0
    var moviesList: MutableLiveData<MutableList<UIMovie>> = MutableLiveData(mutableListOf())
    private var selectedId = -1

    var changedMovie = MutableLiveData<UIMovie?>(null)
    val selectedMovie = MutableLiveData<UIDetailedMovie?>(null)

    fun getMovies(searchPrompt: String?) {
        viewModelScope.launch {
            val remoteList = moviesRepository.getMovies(currentPage, searchPrompt).getOrDefault(emptyList())
            val list = remoteList.map(this@MoviesViewModel::domainMovieToMovie)
            if (currentPage == 1) {
                moviesList.value = ArrayList(list)
            } else {
                moviesList.value = ArrayList(moviesList.value).apply {
                    addAll(list)
                }
            }
            for (i in remoteList.indices)
                loadMovieImage(list[i], remoteList[i])
        }
    }

    private suspend fun loadMovieImage(movie: UIMovie, domainMovie: DomainMovie) {
        moviesRepository.getMoviePoster(domainMovie.imagePath).getOrNull()?.let {
            movie.bmp = BitmapFactory.decodeStream(it)
            changedMovie.value = movie
        }
    }

    fun setSelectedMovie(index: Int) {
        selectedId = moviesList.value!![index].id
    }

    fun getDetailedMovie() {
        viewModelScope.launch {
            val domainDetailedMovie = moviesRepository.getDetailedMovie(selectedId).getOrThrow()
            val detailedMovie = domainDetailedMovieToDetailedMovie(domainDetailedMovie)
            selectedMovie.value = detailedMovie
            selectedMovie.value = selectedMovie.value!!.apply {
                bmp = BitmapFactory.decodeStream(
                    moviesRepository.getMoviePoster(domainDetailedMovie.imagePath).getOrNull()
                )
            }
        }
    }

    private fun domainMovieToMovie(domainMovie: DomainMovie) =
        with(domainMovie) { UIMovie(id, title, description) }

    private fun domainDetailedMovieToDetailedMovie(domainDetailedMovie: DomainDetailedMovie) =
        with(domainDetailedMovie) { UIDetailedMovie(title, description, releaseDate, directors, cast, genres) }
}