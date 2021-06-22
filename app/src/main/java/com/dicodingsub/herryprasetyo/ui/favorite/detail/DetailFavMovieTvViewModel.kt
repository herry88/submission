package com.dicodingsub.herryprasetyo.ui.favorite.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity

class DetailFavMovieTvViewModel(private val repo: MovieRepository) : ViewModel() {

    private var movieId = MutableLiveData<String>()
    private var tvShowId = MutableLiveData<String>()

    var movieFav: LiveData<MovieEntity?> = Transformations.switchMap(movieId, repo::getFavMovieById)

    var tvFav: LiveData<TvEntity?> = Transformations.switchMap(tvShowId, repo::getFavTvById)

    fun setMovieId(movieId: String) {
        this.movieId.value = movieId
    }

    fun setTvShowId(tvShowId: String) {
        this.tvShowId.value = tvShowId
    }

}