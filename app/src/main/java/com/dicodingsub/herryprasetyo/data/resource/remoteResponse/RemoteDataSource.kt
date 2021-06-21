package com.dicodingsub.herryprasetyo.data.resource.remoteResponse

import android.util.Log
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.detail.DetailMovieResponse
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.detail.DetailTvResponse
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.movie.PopularMoviesResponse
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.movie.ResultMovie
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.tvshow.PopularTvResponse
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.tvshow.ResultsTv
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

        const val TAG = "RemoteData source"

    }


    fun getPopularMovie(callback: LoadMovieCallback) {
        EspressoIdlingResource.increment()
        apiClient.create().getMovies().enqueue(object : Callback<PopularMoviesResponse> {
            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                callback.onMovieReceived(null)
                EspressoIdlingResource.decrement()
            }

            override fun onResponse(
                call: Call<PopularMoviesResponse>,
                response: Response<PopularMoviesResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onMovieReceived(response.body()?.results)

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    callback.onMovieReceived(null)

                }
                EspressoIdlingResource.decrement()

            }
        })
    }

    fun getTvShows(callback: LoadTvShowsCallback) {
        EspressoIdlingResource.increment()
        apiClient.create().getTvShows().enqueue(object : Callback<PopularTvResponse> {
            override fun onFailure(call: Call<PopularTvResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                callback.onTvShowsReceived(null)
                EspressoIdlingResource.decrement()
            }

            override fun onResponse(
                call: Call<PopularTvResponse>,
                responsePopular: Response<PopularTvResponse>
            ) {
                if (responsePopular.isSuccessful) {
                    callback.onTvShowsReceived(responsePopular.body()?.results)

                } else {
                    Log.e(TAG, "onFailure: ${responsePopular.message()}")
                    callback.onTvShowsReceived(null)

                }
                EspressoIdlingResource.decrement()

            }
        })
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

    interface LoadMovieCallback {
        fun onMovieReceived(movieResponse: List<ResultMovie>?)
    }

    interface LoadTvShowsCallback {
        fun onTvShowsReceived(tvResponse: List<ResultsTv>?)
    }

    interface LoadDetailMovieCallback {
        fun onDetailMovieReceived(data: DetailMovieResponse?)
    }


    interface LoadDetailTvCallback {
        fun onDetailTvReceived(data: DetailTvResponse?)
    }

}