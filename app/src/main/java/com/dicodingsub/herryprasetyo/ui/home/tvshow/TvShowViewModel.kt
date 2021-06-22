package com.dicodingsub.herryprasetyo.ui.home.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import com.dicodingsub.herryprasetyo.data.source.remote.factory.PopularTvDataSourceFactory
import com.dicodingsub.herryprasetyo.vo.NetworkState

class TvShowViewModel(
    private val repo: MovieRepository,
    private val dataSourceFactory: PopularTvDataSourceFactory
) : ViewModel() {


    var networkState: LiveData<NetworkState>? = null

    init {
        networkState =
            Transformations.switchMap(dataSourceFactory.mutableLiveData) { dataSource ->
                dataSource.networkState
            }
    }


    fun getTVShows(): LiveData<PagedList<TvEntity>> = repo.getTvShows(dataSourceFactory)
}