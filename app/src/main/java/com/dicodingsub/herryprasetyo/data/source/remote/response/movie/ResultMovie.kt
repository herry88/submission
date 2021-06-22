package com.dicodingsub.herryprasetyo.data.source.remote.response.movie

import com.google.gson.annotations.SerializedName


data class ResultMovie(

    @SerializedName("title") val title: String,
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val poster_path: String
)