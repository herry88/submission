package com.dicodingsub.herryprasetyo.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import com.dicodingsub.herryprasetyo.data.source.local.room.MovieTvDao

class LocalDataSource private constructor(private val movieTvDao: MovieTvDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null
        fun getInstance(dao: MovieTvDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(dao)
    }

    fun getAllMovie(): DataSource.Factory<Int, MovieEntity> = movieTvDao.getMovies()


    fun getMovieById(id: String): LiveData<MovieEntity> =
        movieTvDao.getMovieById(id)

    fun insertMovies(modelMovies: MovieEntity) = movieTvDao.insertFavMovies(modelMovies)

    fun deleteFavMovie(modelMovies: MovieEntity) = movieTvDao.deleteFavMovie(modelMovies)


    fun getAllTv(): DataSource.Factory<Int, TvEntity> = movieTvDao.getTvs()


    fun getTvById(id: String): LiveData<TvEntity> =
        movieTvDao.getTvById(id)

    fun insertTvs(modelMovies: TvEntity) = movieTvDao.insertTv(modelMovies)

    fun deleteFavMovie(modelMovies: TvEntity) = movieTvDao.deleteFavTv(modelMovies)


}