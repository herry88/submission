package com.dicodingsub.herryprasetyo.data.source.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.data.source.remote.response.movie.PopularMoviesResponse
import com.dicodingsub.herryprasetyo.data.source.remote.response.movie.ResultMovie
import com.dicodingsub.herryprasetyo.util.EspressoIdlingResource
import com.dicodingsub.herryprasetyo.vo.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PagedMoviesDataSource : PageKeyedDataSource<Int, MovieEntity>() {

    var networkState = MutableLiveData<NetworkState>()
    var initialLoading = MutableLiveData<NetworkState>()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieEntity>
    ) {
        initialLoading.postValue(NetworkState.LOADING)
        networkState.postValue(NetworkState.LOADING)
        EspressoIdlingResource.increment()
        fetchData(1) {
            callback.onResult(it, null, 2)
        }
        EspressoIdlingResource.decrement()

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieEntity>) {
        networkState.postValue(NetworkState.LOADING)
        fetchData(params.key) {
            callback.onResult(it, params.key + 1)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieEntity>
    ) {
    }

    private fun fetchData(page: Int, callback: (List<MovieEntity>) -> Unit) {
        EspressoIdlingResource.increment()
        val call = ApiClient().create().getMovies(page)
        call.enqueue(object : Callback<PopularMoviesResponse> {
            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                val errorMessage = t.message
                networkState.postValue(
                    NetworkState(
                        NetworkState.Status.FAILED,
                        errorMessage!!
                    )
                )
                EspressoIdlingResource.decrement()
            }

            override fun onResponse(
                call: Call<PopularMoviesResponse>,
                response: Response<PopularMoviesResponse>
            ) {
                if (response.isSuccessful) {
                    val popularMoviesResponse = response.body()
                    popularMoviesResponse?.let {
                        initialLoading.postValue(NetworkState.LOADED)
                        networkState.postValue(NetworkState.LOADED)
                        callback(convertToEntity(it.results))
                    }
                } else {
                    initialLoading.postValue(
                        NetworkState(NetworkState.Status.FAILED, response.message())
                    )
                    networkState.postValue(
                        NetworkState(NetworkState.Status.FAILED, response.message())
                    )
                }
                EspressoIdlingResource.decrement()
            }
        })
    }

    private fun convertToEntity(listResponse: List<ResultMovie>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        for (response in listResponse) {
            val course = MovieEntity(
                response.id.toString(),
                response.poster_path,
                response.title
            )
            movieList.add(course)
        }
        return movieList
    }

}