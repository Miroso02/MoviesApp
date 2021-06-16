package com.example.movies

import com.example.movies.domain.MoviesRepository
import com.example.movies.ui.view.MoviesListFragment
import com.example.movies.ui.view.SelectedMovieFragment
import com.example.movies.ui.viewModel.MoviesViewModel
import dagger.Component
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ApplicationModule2::class])
interface ApplicationComponent {
    fun vmProvider(): MoviesVMProvider
    fun injectVMListFragment(fragment: MoviesListFragment)
    fun injectVMSelectedFragment(fragment: SelectedMovieFragment)
}

@Singleton
class MoviesVMProvider @Inject constructor(repository: MoviesRepository) {
    val instance = MoviesViewModel(repository)
}