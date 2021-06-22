package com.dicodingsub.herryprasetyo.ui.detail.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity

class DetailTvViewModel(private val repo: MovieRepository) : ViewModel() {

    private var tvShowId = MutableLiveData<String>()

    fun setTvShowId(tvShowId: String) {
        this.tvShowId.value = tvShowId
    }


    var tv: LiveData<TvEntity?> = Transformations.switchMap(tvShowId, repo::getDetailTv)


    var tvFav: LiveData<TvEntity?> = Transformations.switchMap(tvShowId, repo::getFavTvById)


    fun setFavTv() {
        if (tvFav.value == null) {
            tv.value?.let {
                repo.insertFavTv(it)
            }
        } else {
            tv.value?.let {
                repo.deleteFavTv(it)
            }
        }
    }
}