package com.example.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.R
import com.example.movies.databinding.Fragment1LayoutBinding
import com.example.movies.model.movies
import com.example.movies.viewModel.AllMoviesViewModel
import com.example.movies.viewModel.MoviesAdapter

class AllMoviesFragment : Fragment(R.layout.fragment1_layout) {
    private val moviesVM: AllMoviesViewModel by activityViewModels()
    private var _binding: Fragment1LayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = Fragment1LayoutBinding.inflate(layoutInflater, container, false)
        binding.recyclerView.adapter = MoviesAdapter(movies, this::setSelected)
        binding.recyclerView.layoutManager = GridLayoutManager(this.context, 2)
        moviesVM.getMovies {
            binding.recyclerView.adapter = MoviesAdapter(moviesVM.moviesList, this::setSelected)
            binding.recyclerView.layoutManager = GridLayoutManager(this.context, 2)
        }
        return binding.root
    }

    private fun setSelected(id: Int) {
        moviesVM.setSelected(id)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<SelectedMovieFragment>(R.id.fragment_container_view)
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}