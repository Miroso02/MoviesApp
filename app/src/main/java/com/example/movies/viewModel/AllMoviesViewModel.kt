package com.example.movies.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.retrofitStuff.MoviesRepository
import com.example.movies.retrofitStuff.MyMoviesRepository
import com.example.movies.model.Movie
import com.example.movies.retrofitStuff.retrofitModel.RemoteMovie
import kotlinx.coroutines.launch

class AllMoviesViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository = MyMoviesRepository()
    val selectedMovie = MutableLiveData<Movie?>(null)
    var moviesList: MutableLiveData<ArrayList<Movie>> = MutableLiveData(arrayListOf())
    var currentChangedMovie = -1

    fun getMovies() {
        if (moviesList.value?.size == 0) {
            viewModelScope.launch {
                val remoteList = moviesRepository.getMoviesList()
                val list = remoteList.map(this@AllMoviesViewModel::remoteMovieToMovie)
                moviesList.value?.addAll(list)
                moviesList.notifyObserver()

                loadMoviesImages(remoteList)
                Log.v("movieVM", "loaded ${list.size} movies")
            }
        }
    }

    private suspend fun loadMoviesImages(remoteList: List<RemoteMovie>) {
        for (i in remoteList.indices) {
            moviesList.value?.get(i)?.bmp = moviesRepository.getMoviePoster(remoteList[i])
            currentChangedMovie = i
            moviesList.notifyObserver()
        }
    }

    fun setSelected(id: Int) {
        selectedMovie.value = moviesList.value?.get(id)
    }

    private fun remoteMovieToMovie(remote: RemoteMovie) =
        with(remote) { Movie(id, title, overview) }

    private fun MutableLiveData<*>.notifyObserver() {
        this.value = this.value
    }
}