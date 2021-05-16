package com.example.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.movies.viewModel.AllMoviesViewModel
import com.example.movies.R
import com.example.movies.databinding.Fragment2LayoutBinding
import com.example.movies.model.Movie

class SelectedMovieFragment : Fragment(R.layout.fragment2_layout) {
    private val moviesVM: AllMoviesViewModel by activityViewModels()
    private var _binding: Fragment2LayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = Fragment2LayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieObserver = Observer<Movie?> { movie ->
            /*
            // fancy looking code :З
            with(binding) { listOf(mTitle, mDesc, mReleaseDate, mDirectors, mCast, mGenres) }
                .zip(
            with(movie) { listOf(title, description, releaseDate, directors, cast, genres) })
                .forEach { (textView, text) -> textView.text = text }
            */
            with(binding) {
                mTitle.text = movie.title
                mDesc.text = movie.description
                mReleaseDate.text = movie.releaseDate
                mDirectors.text = movie.directors
                mCast.text = movie.cast
                mGenres.text = movie.genres
            }
        }
        moviesVM.selectedMovie.observe(this, movieObserver)
    }
}