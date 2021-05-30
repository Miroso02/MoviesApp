package com.example.movies.viewModel

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.model.Movie

class MoviesAdapter(private val onClick: (Int) -> Unit) :
    ListAdapter<Movie, MoviesAdapter.MyListViewHolder>(MovieDiffCallback) {
    class MyListViewHolder(itemView: View, val onClick: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.movieTtl)
        private val description: TextView = itemView.findViewById(R.id.movieDesc)
        private val poster: ImageView = itemView.findViewById(R.id.moviePoster)

        init {
            itemView.setOnClickListener {
                Log.v("cursor position", adapterPosition.toString())
                onClick(adapterPosition)
            }
        }

        fun bind(movie: Movie) {
            title.text = movie.title
            description.text = movie.description
            poster.setImageBitmap(movie.bmp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MyListViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem == newItem
}