package com.dicodingsub.herryprasetyo.data.InjectionData

import android.content.Context
import com.dicodingsub.herryprasetyo.data.MovieRepository
import com.dicodingsub.herryprasetyo.data.local.LocalDataSource
import com.dicodingsub.herryprasetyo.data.local.room.TheMovieDatabase
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.ApiClient
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.RemoteDataSource
import com.dicodingsub.herryprasetyo.util.AppExecutors

object Injection {

    fun provideRepository(context: Context): MovieRepository {
        val database = TheMovieDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance(ApiClient())
        val localDataSource = LocalDataSource.getInstance(database.movieTvDao())
        val appExecutors = AppExecutors()
        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

}