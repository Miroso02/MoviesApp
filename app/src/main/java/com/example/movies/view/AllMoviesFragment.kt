package com.example.movies.view

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.databinding.Fragment1LayoutBinding
import com.example.movies.model.Movie
import com.example.movies.viewModel.AllMoviesViewModel
import com.example.movies.viewModel.MoviesAdapter

class AllMoviesFragment : Fragment(R.layout.fragment1_layout) {
    private val moviesVM: AllMoviesViewModel by activityViewModels()
    private var _binding: Fragment1LayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = Fragment1LayoutBinding.inflate(layoutInflater, container, false)
        adapter = MoviesAdapter(moviesVM.moviesList.value ?: arrayListOf(), this::setSelected)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        moviesVM.getMovies()
        val observer = Observer<ArrayList<Movie>> { newValue ->
            for (movie in newValue) {
                if (movie.bmp == null)
                    movie.bmp = BitmapFactory.decodeResource(resources, R.drawable.loading_poster)
            }
            val i = moviesVM.currentChangedMovie
            if (i == -1) {
                adapter.movies = newValue
                adapter.notifyDataSetChanged()
            }
            else {
                adapter.movies[i] = newValue[i]
                adapter.notifyItemChanged(i)
                moviesVM.currentChangedMovie = -1
            }
        }

        moviesVM.moviesList.observe(this, observer)
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