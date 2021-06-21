package com.dicodingsub.herryprasetyo.ui.home

import com.dicodingsub.herryprasetyo.model.MovieEntity


interface ItemListCallback {
    fun onItemCardClicked(data: MovieEntity)
}