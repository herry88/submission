package com.dicodingsub.herryprasetyo.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import com.dicodingsub.herryprasetyo.data.source.remote.factory.PopularMoviesDataSourceFactory
import com.dicodingsub.herryprasetyo.data.source.remote.factory.PopularTvDataSourceFactory

interface MovieDataSource {

    fun getMovies(dataSourceFactory: PopularMoviesDataSourceFactory): LiveData<PagedList<MovieEntity>>
    fun getTvShows(dataSourceFactory: PopularTvDataSourceFactory): LiveData<PagedList<TvEntity>>


    fun getDetailMovie(id: String): LiveData<MovieEntity?>
    fun getDetailTv(id: String): LiveData<TvEntity?>

    fun getFavMovies(): LiveData<PagedList<MovieEntity>>
    fun getFavTvs(): LiveData<PagedList<TvEntity>>

    fun insertFavMovie(entity: MovieEntity)
    fun deleteFavMovie(entity: MovieEntity)

    fun insertFavTv(entity: TvEntity)
    fun deleteFavTv(entity: TvEntity)

    fun getFavMovieById(id: String): LiveData<MovieEntity>
    fun getFavTvById(id: String): LiveData<TvEntity>
}