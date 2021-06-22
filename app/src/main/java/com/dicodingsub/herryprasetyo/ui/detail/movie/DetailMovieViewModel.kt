package com.dicodingsub.herryprasetyo.ui.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity

class DetailMovieViewModel(private val repo: MovieRepository) : ViewModel() {

    private var movieId = MutableLiveData<String>()

    fun setMovieId(movieId: String) {
        this.movieId.value = movieId
    }

    var movie: LiveData<MovieEntity?> = Transformations.switchMap(movieId) { movieId ->
        repo.getDetailMovie(movieId)
    }


    var movieFav: LiveData<MovieEntity?> = Transformations.switchMap(movieId, repo::getFavMovieById)

    fun setFavMovie() {
        if (movieFav.value == null) {
            movie.value?.let {
                repo.insertFavMovie(it)
            }
        } else {
            movie.value?.let {
                repo.deleteFavMovie(it)
            }
        }
    }


}