package com.dicodingsub.herryprasetyo.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.di.Injection
import com.dicodingsub.herryprasetyo.data.source.remote.factory.PopularTvDataSourceFactory
import com.dicodingsub.herryprasetyo.ui.home.tvshow.TvShowViewModel


class TvViewModelFactory private constructor(
    private val mAcademyRepository: MovieRepository,
    private val popularTvDataSourceFactory: PopularTvDataSourceFactory
) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: TvViewModelFactory? = null

        fun getInstance(context: Context): TvViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: TvViewModelFactory(
                    Injection.provideRepository(context),
                    PopularTvDataSourceFactory()
                )
            }

    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                TvShowViewModel(mAcademyRepository, popularTvDataSourceFactory) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}