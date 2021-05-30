package com.example.movies.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.model.DetailedMovie
import com.example.movies.model.Movie
import com.example.movies.retrofitStuff.MoviesRepository
import com.example.movies.retrofitStuff.MyMoviesRepository
import com.example.movies.retrofitStuff.retrofitModel.RemoteMovie
import kotlinx.coroutines.launch

class AllMoviesViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository = MyMoviesRepository
    private var selectedId = -1
    val selectedMovie = MutableLiveData<DetailedMovie?>(null)
    var moviesList: MutableLiveData<ArrayList<Movie>> = MutableLiveData(arrayListOf())

    fun getMovies() {
        if (moviesList.value?.size == 0) {
            viewModelScope.launch {
                val remoteList = moviesRepository.getMoviesList()
                val list = remoteList.map(this@AllMoviesViewModel::remoteMovieToMovie)
                moviesList.value = ArrayList(list)

                Log.v("movieVM", "loaded ${list.size} movies")
                loadMoviesImages(remoteList)
            }
        }
    }

    fun getDetailedMovie() {
        viewModelScope.launch {
            Log.v("moviesVM", "loading detailed movie...")
            val movie = moviesRepository.getDetailedMovie(selectedId)
            Log.v("moviesVM", "loaded movie")
            val credits = moviesRepository.getMovieCredits(selectedId)
            Log.v("moviesVM", "loaded credits")
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

            selectedMovie.value = DetailedMovie(selectedMovie.value!!).apply {
                bmp = moviesRepository.getMoviePoster(movie.poster_path ?: "")
            }
        }
    }

    fun setSelectedMovie(index: Int) {
        selectedId = moviesList.value!![index].id
    }

    private suspend fun loadMoviesImages(remoteList: List<RemoteMovie>) {
        for (i in remoteList.indices) {
            moviesList.value = ArrayList(moviesList.value).also {
                it[i] = Movie(it[i]).apply { bmp = moviesRepository.getMoviePoster(remoteList[i].poster_path ?: "") }
            }
        }
    }

    private fun remoteMovieToMovie(remote: RemoteMovie) =
        with(remote) { Movie(id, title, overview) }
}