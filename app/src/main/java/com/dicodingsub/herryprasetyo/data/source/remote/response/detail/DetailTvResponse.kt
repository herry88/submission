package com.dicodingsub.herryprasetyo.data.source.remote.response.detail

import com.google.gson.annotations.SerializedName

data class DetailTvResponse(

    @SerializedName("success") val success: Boolean? = true,
    @SerializedName("genres") val genres: List<Genres>,
    @SerializedName("id") val id: Int,
    @SerializedName("first_air_date") val first_air_date: String,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("vote_average") val vote_average: Double
)