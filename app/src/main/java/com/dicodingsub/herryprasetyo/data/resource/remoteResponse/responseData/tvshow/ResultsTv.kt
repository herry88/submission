package com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.tvshow

import com.google.gson.annotations.SerializedName


data class ResultsTv(

    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val poster_path: String
)