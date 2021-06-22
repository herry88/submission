package com.dicodingsub.herryprasetyo.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dicodingsub.herryprasetyo.BuildConfig
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.loadImageFromUrl(src: String) {
    Glide.with(context)
        .load("${BuildConfig.IMAGE_URL}$src")
        .into(this)
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun String.convertDateFormat(): String {
    val dateRelease = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
    return if (dateRelease != null) {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(dateRelease)
    } else {
        ""
    }
}