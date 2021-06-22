package com.dicodingsub.herryprasetyo.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.di.Injection
import com.dicodingsub.herryprasetyo.ui.detail.movie.DetailMovieViewModel
import com.dicodingsub.herryprasetyo.ui.detail.tv.DetailTvViewModel
import com.dicodingsub.herryprasetyo.ui.favorite.detail.DetailFavMovieTvViewModel
import com.dicodingsub.herryprasetyo.ui.favorite.favmovie.MovieFavViewModel
import com.dicodingsub.herryprasetyo.ui.favorite.favtv.TvFavViewModel

class ViewModelFactory private constructor(private val mAcademyRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }

    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(
                    mAcademyRepository
                ) as T
            }
            modelClass.isAssignableFrom(DetailTvViewModel::class.java) -> {
                DetailTvViewModel(
                    mAcademyRepository
                ) as T
            }
            modelClass.isAssignableFrom(MovieFavViewModel::class.java) -> {
                MovieFavViewModel(
                    mAcademyRepository
                ) as T
            }
            modelClass.isAssignableFrom(DetailFavMovieTvViewModel::class.java) -> {
                DetailFavMovieTvViewModel(
                    mAcademyRepository
                ) as T
            }
            modelClass.isAssignableFrom(TvFavViewModel::class.java) -> {
                TvFavViewModel(
                    mAcademyRepository
                ) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}