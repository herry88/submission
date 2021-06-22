package com.dicodingsub.herryprasetyo.data.source.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import com.dicodingsub.herryprasetyo.data.source.remote.response.tvshow.PopularTvResponse
import com.dicodingsub.herryprasetyo.data.source.remote.response.tvshow.ResultsTv
import com.dicodingsub.herryprasetyo.util.EspressoIdlingResource
import com.dicodingsub.herryprasetyo.vo.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PagedTvsDataSource : PageKeyedDataSource<Int, TvEntity>() {

    var networkState = MutableLiveData<NetworkState>()
    var initialLoading = MutableLiveData<NetworkState>()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TvEntity>
    ) {
        initialLoading.postValue(NetworkState.LOADING)
        networkState.postValue(NetworkState.LOADING)
        fetchData(1) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TvEntity>) {
        networkState.postValue(NetworkState.LOADING)
        fetchData(params.key) {
            callback.onResult(it, params.key + 1)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TvEntity>
    ) {
    }

    private fun fetchData(page: Int, callback: (List<TvEntity>) -> Unit) {
        EspressoIdlingResource.increment()
        val call = ApiClient().create().getTvShows(page)
        call.enqueue(object : Callback<PopularTvResponse> {
            override fun onFailure(call: Call<PopularTvResponse>, t: Throwable) {
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
                call: Call<PopularTvResponse>,
                response: Response<PopularTvResponse>
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

    private fun convertToEntity(listResponse: List<ResultsTv>): List<TvEntity> {
        val movieList = ArrayList<TvEntity>()
        for (response in listResponse) {
            val course = TvEntity(
                response.id.toString(),
                response.poster_path,
                response.name
            )
            movieList.add(course)
        }
        return movieList
    }

}