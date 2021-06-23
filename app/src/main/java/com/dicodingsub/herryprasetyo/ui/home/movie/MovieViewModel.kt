package com.dicodingsub.herryprasetyo.ui.home.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.data.source.remote.factory.PopularMoviesDataSourceFactory
import com.dicodingsub.herryprasetyo.vo.NetworkState

class MovieViewModel(
    private val repo: MovieRepository, private var dataSourceFactory: PopularMoviesDataSourceFactory
) : ViewModel() {


    var networkState: LiveData<NetworkState>? = null

    init {
        networkState =
            Transformations.switchMap(dataSourceFactory.mutableLiveData) { dataSource ->
                dataSource.networkState
            }
    }


    fun getMovies(): LiveData<PagedList<MovieEntity>> = repo.getMovies(dataSourceFactory)

}