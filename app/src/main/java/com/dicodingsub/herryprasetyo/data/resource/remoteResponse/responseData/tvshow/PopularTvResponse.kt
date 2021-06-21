package com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.tvshow

import com.google.gson.annotations.SerializedName


data class PopularTvResponse(

    @SerializedName("results") val results: List<ResultsTv>
)