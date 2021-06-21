package com.dicodingsub.herryprasetyo.ui.home.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.model.MovieEntity

class TvShowViewModel(private val repo: MovieRepository) : ViewModel() {


    fun getTVShows(): LiveData<List<MovieEntity>> = repo.getTvShows()
}