package com.example.movies.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.MyApplication
import com.example.movies.R
import com.example.movies.databinding.MoviesListLayoutBinding
import com.example.movies.ui.model.UIMovie
import com.example.movies.ui.viewModel.MoviesViewModel
import com.example.movies.ui.viewModel.MoviesAdapter

class MoviesListFragment : Fragment(R.layout.movies_list_layout) {
    private lateinit var moviesVM: MoviesViewModel
    private var _binding: MoviesListLayoutBinding? = null
    private val binding get() = _binding!!
    private var adapter = MoviesAdapter(this::setSelected)
    private var searchPrompt: String? = null
    private var defaultImageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesVM = (activity?.application as MyApplication).appContainer!!.moviesViewModel

        val listObserver = Observer<MutableList<UIMovie>> { newList ->
            for (movie in newList) {
                if (movie.bmp == null)
                    movie.bmp = defaultImageBitmap
            }
            adapter.submitList(newList)
        }
        val itemObserver = Observer<UIMovie?> { movie ->
            adapter.notifyItemChanged(moviesVM.moviesList.value!!.indexOf(movie))
        }
        with(moviesVM) {
            moviesList.observe(this@MoviesListFragment, listObserver)
            changedMovie.observe(this@MoviesListFragment, itemObserver)
            currentPage = 1
            getMovies(null)
        }
        defaultImageBitmap = BitmapFactory.decodeResource(resources, R.drawable.loading_poster)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MoviesListLayoutBinding.inflate(layoutInflater, container, false)
        binding.recyclerView.also {
            it.adapter = adapter
            val layoutManager = LinearLayoutManager(this.context)
            it.layoutManager = layoutManager
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (layoutManager.findLastVisibleItemPosition() == moviesVM.currentPage * 20 - 1) {
                        moviesVM.currentPage++
                        moviesVM.getMovies(searchPrompt)
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }
        binding.searchBar.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    getSearchResults()
                    true
                }
                else -> false
            }
        }
        binding.search.setOnClickListener {
            getSearchResults()
        }
        return binding.root
    }

    private fun getSearchResults() {
        moviesVM.currentPage = 1
        searchPrompt = binding.searchBar.text.toString()
        moviesVM.getMovies(searchPrompt)
        val inputManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            activity?.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun setSelected(index: Int) {
        moviesVM.setSelectedMovie(index)
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