package com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.detail

import com.google.gson.annotations.SerializedName


data class Genres(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)