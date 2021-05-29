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

class MoviesAdapter(var movies: ArrayList<Movie>, private val onClick: (Int) -> Unit) :
    ListAdapter<Movie, MoviesAdapter.MyListViewHolder>(MovieDiffCallback) {
    class MyListViewHolder(itemView: View, val onClick: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.movieTtl)
        val description: TextView = itemView.findViewById(R.id.movieDesc)
        val poster: ImageView = itemView.findViewById(R.id.moviePoster)

        init {
            itemView.setOnClickListener {
                Log.v("cursor position", adapterPosition.toString())
                onClick(adapterPosition)
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
        holder.poster.setImageBitmap(movies[position].bmp)
    }

    override fun getItemCount() = movies.size
}

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.id == newItem.id
}