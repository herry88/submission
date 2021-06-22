package com.dicodingsub.herryprasetyo.data.source.remote.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.data.source.remote.PagedMoviesDataSource

class PopularMoviesDataSourceFactory : DataSource.Factory<Int, MovieEntity>() {

    val mutableLiveData = MutableLiveData<PagedMoviesDataSource>()
    private lateinit var movieDataSource: PagedMoviesDataSource


    override fun create(): DataSource<Int, MovieEntity> {
        this.movieDataSource = PagedMoviesDataSource()
        mutableLiveData.postValue(movieDataSource)
        return movieDataSource
    }

    companion object {
        private const val PAGE_SIZE = 10

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }


}