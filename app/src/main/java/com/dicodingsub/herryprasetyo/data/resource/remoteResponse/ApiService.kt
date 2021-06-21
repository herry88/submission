package com.dicodingsub.herryprasetyo.data.resource.remoteResponse

import com.dicodingsub.herryprasetyo.BuildConfig
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.detail.DetailMovieResponse
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.detail.DetailTvResponse
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.movie.PopularMoviesResponse
import com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.tvshow.PopularTvResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/3/movie/popular")
    fun getMovies(@Query("api_key") apiKey: String = BuildConfig.API_KEY): Call<PopularMoviesResponse>

    @GET("/3/discover/tv")
    fun getTvShows(@Query("api_key") apiKey: String = BuildConfig.API_KEY): Call<PopularTvResponse>

    @GET("/3/movie/{id}")
    fun getDetailMovie(@Path("id") id: String, @Query("api_key") apiKey: String = BuildConfig.API_KEY): Call<DetailMovieResponse>

    @GET("/3/tv/{id}")
    fun getDetailTv(@Path("id") id: String, @Query("api_key") apiKey: String = BuildConfig.API_KEY): Call<DetailTvResponse>

}