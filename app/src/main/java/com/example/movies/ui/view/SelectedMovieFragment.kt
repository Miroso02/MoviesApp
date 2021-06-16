package com.example.movies.ui.view

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.movies.MoviesVMProvider
import com.example.movies.MyApplication
import com.example.movies.R
import com.example.movies.databinding.SelectedMovieLayoutBinding
import com.example.movies.ui.model.UIDetailedMovie
import com.example.movies.ui.viewModel.MoviesViewModel
import javax.inject.Inject

class SelectedMovieFragment : Fragment(R.layout.selected_movie_layout) {
    @Inject lateinit var vmProvider: MoviesVMProvider
    private val moviesVM get() = vmProvider.instance
    private var _binding: SelectedMovieLayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = SelectedMovieLayoutBinding.inflate(inflater)
        moviesVM.getDetailedMovie()
        return binding.root
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as MyApplication).appComponent.injectVMSelectedFragment(this)
        super.onCreate(savedInstanceState)

        val movieObserver = Observer<UIDetailedMovie?> { movie ->
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}