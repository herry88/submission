package com.dicodingsub.herryprasetyo.ui.home.movie

import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity


interface ItemMovieListCallback {
    fun onItemMovieClicked(data: MovieEntity)
}