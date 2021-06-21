package com.dicodingsub.herryprasetyo.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dicodingsub.herryprasetyo.data.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.data.local.entity.TvEntity

@Dao
interface MovieTvDao {


    @Query("SELECT * FROM tableMovie")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Transaction
    @Query("SELECT * FROM tableMovie WHERE id = :movieId")
    fun getMovieById(movieId: String): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavMovies(modelMovies: MovieEntity)

    @Delete
    fun deleteFavMovie(movie: MovieEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTv(modelMovies: TvEntity)

    @Query("SELECT * FROM tableTv")
    fun getTvs(): DataSource.Factory<Int, TvEntity>

    @Transaction
    @Query("SELECT * FROM tableTv WHERE id = :movieId")
    fun getTvById(movieId: String): LiveData<TvEntity>

    @Delete
    fun deleteFavTv(tv: TvEntity)


}