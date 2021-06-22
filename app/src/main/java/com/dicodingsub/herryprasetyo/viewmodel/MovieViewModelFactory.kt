package com.dicodingsub.herryprasetyo.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.di.Injection
import com.dicodingsub.herryprasetyo.data.source.remote.factory.PopularMoviesDataSourceFactory
import com.dicodingsub.herryprasetyo.ui.home.movie.MovieViewModel


class MovieViewModelFactory private constructor(
    private val mAcademyRepository: MovieRepository,
    private val popularMoviesDataSourceFactory: PopularMoviesDataSourceFactory
) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: MovieViewModelFactory? = null

        fun getInstance(context: Context): MovieViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: MovieViewModelFactory(
                    Injection.provideRepository(context),
                    PopularMoviesDataSourceFactory()
                )
            }

    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(mAcademyRepository, popularMoviesDataSourceFactory) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}