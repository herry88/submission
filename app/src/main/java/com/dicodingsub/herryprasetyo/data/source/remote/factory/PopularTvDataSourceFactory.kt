package com.dicodingsub.herryprasetyo.data.source.remote.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import com.dicodingsub.herryprasetyo.data.source.remote.PagedTvsDataSource

class PopularTvDataSourceFactory : DataSource.Factory<Int, TvEntity>() {

    val mutableLiveData = MutableLiveData<PagedTvsDataSource>()
    private lateinit var tvDataSource: PagedTvsDataSource


    override fun create(): DataSource<Int, TvEntity> {
        this.tvDataSource = PagedTvsDataSource()
        mutableLiveData.postValue(tvDataSource)
        return tvDataSource
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