package com.dicodingsub.herryprasetyo.data.source.remote.response.tvshow

import com.google.gson.annotations.SerializedName


data class PopularTvResponse(

    @SerializedName("results") val results: List<ResultsTv>
)