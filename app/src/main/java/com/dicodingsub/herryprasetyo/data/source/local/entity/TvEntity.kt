package com.dicodingsub.herryprasetyo.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "tableTv")
@Parcelize
data class TvEntity(
    @PrimaryKey
    @NonNull
    var id: String,
    var poster: String? = null,
    var title: String? = null,
    var score: String? = null,
    var release: String? = null,
    var genre: String? = null,
    var description: String? = null
) : Parcelable