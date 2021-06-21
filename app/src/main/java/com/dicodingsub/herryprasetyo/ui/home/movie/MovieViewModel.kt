package com.dicodingsub.herryprasetyo.ui.home.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.model.MovieEntity

class MovieViewModel(private val repo: MovieRepository) : ViewModel() {

    fun getMovies(): LiveData<List<MovieEntity>> = repo.getMovies()

}