package com.dicodingsub.herryprasetyo.ui.favorite.favmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity

class MovieFavViewModel(private val repo: MovieRepository) : ViewModel() {

    fun getMovies(): LiveData<PagedList<MovieEntity>> = repo.getFavMovies()

    fun deleteMovie(data: MovieEntity) {
        repo.deleteFavMovie(data)
    }

}