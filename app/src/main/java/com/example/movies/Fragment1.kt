package com.example.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import com.example.movies.databinding.Fragment1LayoutBinding

class Fragment1: Fragment(R.layout.fragment1_layout) {
    private val model: SelectedMovieViewModel by activityViewModels()
    private var _binding: Fragment1LayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = Fragment1LayoutBinding.inflate(layoutInflater, container, false)

        binding.btn1.setOnClickListener {
            setSelected(0)
        }
        binding.btn2.setOnClickListener {
            setSelected(1)
        }
        return binding.root
    }

    private fun setSelected(id: Int) {
        model.setMovie(id)
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