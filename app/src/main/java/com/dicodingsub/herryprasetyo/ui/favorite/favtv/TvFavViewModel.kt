package com.dicodingsub.herryprasetyo.ui.favorite.favtv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity

class TvFavViewModel(private val repo: MovieRepository) : ViewModel() {

    fun getTvs(): LiveData<PagedList<TvEntity>> = repo.getFavTvs()

    fun deleteTv(data: TvEntity) {
        repo.deleteFavTv(data)
    }

}