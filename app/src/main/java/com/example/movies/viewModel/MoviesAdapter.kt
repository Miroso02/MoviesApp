package com.example.movies.viewModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.model.Movie
import com.example.movies.model.movies

class MoviesAdapter(val movies: List<Movie>, private val onClick: (Int) -> Unit) :
    ListAdapter<Movie, MoviesAdapter.MyListViewHolder>(MovieDiffCallback) {

    class MyListViewHolder(itemView: View, val onClick: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.movieTtl)
        val description: TextView = itemView.findViewById(R.id.movieDesc)

        init {
            itemView.setOnClickListener {
                onClick(movies[adapterPosition].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MyListViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MyListViewHolder, position: Int) {
        holder.title.text = movies[position].title
        holder.description.text = movies[position].description
    }

    override fun getItemCount() = movies.size
}

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.title == newItem.title
}