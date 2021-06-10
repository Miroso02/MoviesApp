package com.example.movies.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.room.Room
import com.example.movies.R
import com.example.movies.db.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesDB = Room.databaseBuilder(applicationContext, MoviesDB::class.java, "movies-database")
            .fallbackToDestructiveMigration()
            .build()
        detailedMoviesDAO = moviesDB!!.detailedMoviesDAO()
        imagesDAO = moviesDB!!.imagesDao()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MoviesListFragment>(R.id.fragment_container_view)
            }
        }
    }
}