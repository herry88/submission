package com.dicodingsub.herryprasetyo.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dicodingsub.herryprasetyo.BuildConfig

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