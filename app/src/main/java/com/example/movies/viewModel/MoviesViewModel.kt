package com.example.movies.viewModel

import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.DomainDetailedMovie
import com.example.movies.domain.DomainMovie
import com.example.movies.domain.MoviesRepository
import com.example.movies.model.DetailedMovie
import com.example.movies.model.Movie
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {
    private val moviesRepository = MoviesRepository()
    var currentPage = 0
    var moviesList: MutableLiveData<MutableList<Movie>> = MutableLiveData(mutableListOf())
    private var selectedId = -1

    var changedMovie = MutableLiveData<Movie?>(null)
    val selectedMovie = MutableLiveData<DetailedMovie?>(null)

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

    private suspend fun loadMovieImage(movie: Movie, domainMovie: DomainMovie) {
        movie.bmp = BitmapFactory.decodeStream(moviesRepository.getMoviePoster(domainMovie.imagePath).getOrNull())
        changedMovie.value = movie
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
        with(domainMovie) { Movie(id, title, description) }

    private fun domainDetailedMovieToDetailedMovie(domainDetailedMovie: DomainDetailedMovie) =
        with(domainDetailedMovie) { DetailedMovie(title, description, releaseDate, directors, cast, genres) }
}