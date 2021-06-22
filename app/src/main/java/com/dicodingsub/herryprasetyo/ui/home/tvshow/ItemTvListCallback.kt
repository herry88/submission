package com.dicodingsub.herryprasetyo.ui.home.tvshow

import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity


interface ItemTvListCallback {
    fun onItemTvClicked(data: TvEntity)

}