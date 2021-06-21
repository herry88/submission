package com.dicodingsub.herryprasetyo.data

import androidx.lifecycle.LiveData
import com.dicodingsub.herryprasetyo.model.MovieEntity

interface MovieDataSource {

    fun getMovies(): LiveData<List<MovieEntity>>
    fun getTvShows(): LiveData<List<MovieEntity>>

    fun getDetailMovie(id: String): LiveData<MovieEntity?>
    fun getDetailTv(id: String): LiveData<MovieEntity?>
}