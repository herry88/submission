package com.dicodingsub.herryprasetyo.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.model.MovieEntity

class DetailViewModel(private val repo: MovieRepository) : ViewModel() {

    private lateinit var movieId: String
    private lateinit var tvShowId: String


    fun getMovieById(): LiveData<MovieEntity?> = repo.getDetailMovie(movieId)

    fun getTvShowById(): LiveData<MovieEntity?> = repo.getDetailTv(tvShowId)


    fun setMovieId(movieId: String) {
        this.movieId = movieId
    }

    fun setTvShowId(tvShowId: String) {
        this.tvShowId = tvShowId
    }

}