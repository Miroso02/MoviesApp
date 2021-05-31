package com.example.movies.viewModel

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
    var currentPage = 0
        private set
    var moviesList: MutableLiveData<ArrayList<Movie>> = MutableLiveData(arrayListOf())
        private set
    private var selectedId = -1
    val selectedMovie = MutableLiveData<DetailedMovie?>(null)

    fun getMovies(pageToLoad: Int) {
        if (pageToLoad > currentPage) {
            currentPage = pageToLoad
            viewModelScope.launch {
                val result = moviesRepository.getMoviesList(currentPage)
                val remoteList = result.getOrThrow()
                val list = remoteList.map(this@AllMoviesViewModel::remoteMovieToMovie)
                moviesList.value = moviesList.value?.apply { addAll(ArrayList(list)) }
                loadMoviesImages(remoteList)
            }
        }
    }

    fun setSelectedMovie(index: Int) {
        selectedId = moviesList.value!![index].id
    }

    fun getDetailedMovie() {
        viewModelScope.launch {
            val movie = moviesRepository.getDetailedMovie(selectedId)
            val credits = moviesRepository.getMovieCredits(selectedId)
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

    private suspend fun loadMoviesImages(remoteList: List<RemoteMovie>) {
        for (i in remoteList.indices) {
            val j = (currentPage - 1) * 20 + i
            moviesList.value = ArrayList(moviesList.value).also {
                it[j] = Movie(it[j]).apply { bmp = moviesRepository.getMoviePoster(remoteList[i].poster_path ?: "") }
            }
        }
    }

    private fun remoteMovieToMovie(remote: RemoteMovie) =
        with(remote) { Movie(id, title, overview) }
}