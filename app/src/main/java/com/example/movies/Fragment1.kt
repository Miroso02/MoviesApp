package com.example.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import com.example.movies.databinding.Fragment1LayoutBinding

class Fragment1 : Fragment(R.layout.fragment1_layout) {
    private val model: SelectedMovieViewModel by activityViewModels()
    private var _binding: Fragment1LayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = Fragment1LayoutBinding.inflate(layoutInflater, container, false)
        binding.recyclerView.adapter = MyListAdapter(movies) {
            setSelected(it)
        }
        return binding.root
    }

    private fun setSelected(value: Movie) {
        model.movie.value = value
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<Fragment2>(R.id.fragment_container_view)
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}