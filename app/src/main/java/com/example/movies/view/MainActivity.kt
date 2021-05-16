package com.example.movies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.movies.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<AllMoviesFragment>(R.id.fragment_container_view)
            }
        }
    }
}