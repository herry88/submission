package com.dicodingsub.herryprasetyo.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import com.dicodingsub.herryprasetyo.data.source.remote.factory.PopularMoviesDataSourceFactory
import com.dicodingsub.herryprasetyo.data.source.remote.factory.PopularTvDataSourceFactory
import com.dicodingsub.herryprasetyo.data.source.remote.response.detail.DetailMovieResponse
import com.dicodingsub.herryprasetyo.data.source.remote.response.detail.DetailTvResponse
import com.dicodingsub.herryprasetyo.util.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiClient: ApiClient) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiClient: ApiClient): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(apiClient)
            }

        const val TAG = "RemoteDataSource"

    }


    fun getPagedPopularMovies(dataSourceFactory: PopularMoviesDataSourceFactory)
            : LiveData<PagedList<MovieEntity>> {
        return LivePagedListBuilder(
            dataSourceFactory,
            PopularMoviesDataSourceFactory.pagedListConfig()
        ).build()
    }

    fun getPagedPopularTv(dataSourceFactory: PopularTvDataSourceFactory)
            : LiveData<PagedList<TvEntity>> {
        return LivePagedListBuilder(
            dataSourceFactory,
            PopularTvDataSourceFactory.pagedListConfig()
        ).build()
    }


    fun getDetailMovie(id: String, callback: LoadDetailMovieCallback) {
        EspressoIdlingResource.increment()
        apiClient.create().getDetailMovie(id).enqueue(object : Callback<DetailMovieResponse> {
            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                callback.onDetailMovieReceived(null)
                EspressoIdlingResource.decrement()
            }

            override fun onResponse(
                call: Call<DetailMovieResponse>,
                responsePopular: Response<DetailMovieResponse>
            ) {
                if (responsePopular.isSuccessful) {
                    responsePopular.body()?.let {
                        callback.onDetailMovieReceived(it)
                    }

                } else {
                    Log.e(TAG, "onFailure: ${responsePopular.message()}")
                    callback.onDetailMovieReceived(null)

                }
                EspressoIdlingResource.decrement()

            }
        })
    }


    fun getDetailTv(id: String, callback: LoadDetailTvCallback) {
        EspressoIdlingResource.increment()
        apiClient.create().getDetailTv(id).enqueue(object : Callback<DetailTvResponse> {
            override fun onFailure(call: Call<DetailTvResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                callback.onDetailTvReceived(null)
                EspressoIdlingResource.decrement()
            }

            override fun onResponse(
                call: Call<DetailTvResponse>,
                responsePopular: Response<DetailTvResponse>
            ) {
                if (responsePopular.isSuccessful) {
                    responsePopular.body()?.let {
                        callback.onDetailTvReceived(it)
                    }

                } else {
                    Log.e(TAG, "onFailure: ${responsePopular.message()}")
                    callback.onDetailTvReceived(null)

                }
                EspressoIdlingResource.decrement()

            }
        })
    }


    interface LoadDetailMovieCallback {
        fun onDetailMovieReceived(data: DetailMovieResponse?)
    }


    interface LoadDetailTvCallback {
        fun onDetailTvReceived(data: DetailTvResponse?)
    }


}