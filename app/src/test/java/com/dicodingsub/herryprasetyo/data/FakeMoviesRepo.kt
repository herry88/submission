@file:Suppress("UNREACHABLE_CODE")

package com.dicodingsub.herryprasetyo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.RemoteDataSource
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.detail.DetailMovieResponse
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.detail.DetailTvResponse
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.movie.ResultMovie
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.tvshow.ResultsTv
import com.dicodingsub.herryprasetyo.model.MovieEntity

class FakeMoviesRepo(private val remoteDataSource: RemoteDataSource): MovieDataSource {
    override fun getMovies(): LiveData<List<MovieEntity>> {
        //lupa hapus
        val movieResult = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getPopularMovie(object : RemoteDataSource.LoadMovieCallback{
            override fun onMovieReceived(movieResponse: List<ResultMovie>?) {
                //lupa hapus
             val movieList = ArrayList<MovieEntity>()
             movieResponse?.let{
                 movies -> for(response in movies){
                     val course = MovieEntity(
                         response.id.toString(),
                         response.poster_path,
                         response.title
                     )
                 movieList.add(course)

             }
                 movieResult.postValue(movieList)
             }
            }
        })
        return movieResult
    }

    override fun getTvShows(): LiveData<List<MovieEntity>> {

        val tvResult = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getTvShows(object : RemoteDataSource.LoadTvShowsCallback{
            override fun onTvShowsReceived(tvResponse: List<ResultsTv>?) {
                val movieList = ArrayList<MovieEntity>()
                tvResponse?.let { tvShow -> for(response in tvShow){
                    val course = MovieEntity(
                        response.id.toString(),
                        response.poster_path,
                        response.name
                    )
                    movieList.add(course)
                }
                    tvResult.postValue(movieList)
                }
            }
        })
        return tvResult
    }

    override fun getDetailMovie(id: String): LiveData<MovieEntity?> {

        val detailResult = MutableLiveData<MovieEntity>()
        remoteDataSource.getDetailMovie(id, object : RemoteDataSource.LoadDetailMovieCallback {
            override fun onDetailMovieReceived(data: DetailMovieResponse?) {
                data?.let { response -> val genresList = response.genres.map { it.name }
                                        val movieEntity = MovieEntity(
                                            response.id.toString(),
                                            response.poster_path,
                                            response.title,
                                            response.vote_average.toString(),
                                            response.release_date,
                                            genresList.joinToString(separator = ", "),
                                            response.overview
                                        )
                    detailResult.postValue(movieEntity)
                }?: detailResult.postValue(null)
            }
        })
        return detailResult
    }

    override fun getDetailTv(id: String): LiveData<MovieEntity?> {

        val detailResult = MutableLiveData<MovieEntity>()
        remoteDataSource.getDetailTv(id, object : RemoteDataSource.LoadDetailTvCallback {
            override fun onDetailTvReceived(data: DetailTvResponse?) {
                data?.let { response ->  val genresList = response.genres.map { it.name }
                val  movieEntity = MovieEntity(
                    response.poster_path,
                    response.name,
                    response.vote_average.toString(),
                    response.first_air_date,
                    genresList.joinToString(separator =  ", "),
                    response.overview)
                    detailResult.postValue(movieEntity)
                }?: detailResult.postValue(null)
            }
        })
        return detailResult
    }

}