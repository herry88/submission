package com.dicodingsub.herryprasetyo.data.resource.remoteResponse.responseData.detail

import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(

    @SerializedName("success") val success: Boolean? = true,
    @SerializedName("genres") val genres: List<Genres> = ArrayList(),
    @SerializedName("id") val id: Int,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val vote_average: Double
)