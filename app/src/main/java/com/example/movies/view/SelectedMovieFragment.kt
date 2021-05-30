package com.example.movies.view

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.movies.R
import com.example.movies.databinding.Fragment2LayoutBinding
import com.example.movies.model.DetailedMovie
import com.example.movies.viewModel.AllMoviesViewModel

class SelectedMovieFragment : Fragment(R.layout.fragment2_layout) {
    private val moviesVM: AllMoviesViewModel by activityViewModels()
    private var _binding: Fragment2LayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = Fragment2LayoutBinding.inflate(inflater)
        moviesVM.getDetailedMovie()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieObserver = Observer<DetailedMovie?> { movie ->
            if (movie != null) {
                with(binding) {
                    mTitle.text = movie.title
                    mDesc.text = movie.description
                    mCast.text = movie.cast
                    mGenres.text = movie.genres
                    mReleaseDate.text = movie.releaseDate
                    mDirectors.text = movie.directors
                    mPoster.setImageBitmap(
                        movie.bmp ?: BitmapFactory.decodeResource(
                            resources,
                            R.drawable.loading_poster
                        )
                    )
                }
            }
        }
        moviesVM.selectedMovie.observe(this, movieObserver)
    }
}