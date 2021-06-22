package com.dicodingsub.herryprasetyo.ui.favorite

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicodingsub.herryprasetyo.R
import com.dicodingsub.herryprasetyo.ui.favorite.favmovie.MovieFavFragment
import com.dicodingsub.herryprasetyo.ui.favorite.favtv.TvFavFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private val TAB_TITLES = intArrayOf(R.string.movie_fav, R.string.tvshow_fav)
    }

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> MovieFavFragment()
            1 -> TvFavFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence? =
        mContext.resources.getString(TAB_TITLES[position])

    override fun getCount(): Int = 2

}