package com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.movie

import com.google.gson.annotations.SerializedName


data class PopularMoviesResponse(
    @SerializedName("results") val results: List<ResultMovie>
)