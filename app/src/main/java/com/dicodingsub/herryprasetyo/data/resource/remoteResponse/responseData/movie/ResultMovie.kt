package com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.movie

import com.google.gson.annotations.SerializedName


data class ResultMovie(

    @SerializedName("title") val title: String,
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val poster_path: String
)