package com.dicodingsub.herryprasetyo.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicodingsub.herryprasetyo.R
import kotlinx.android.synthetic.main.activity_home.*

class FavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpToolbar()
        val sectionPagerAdapter = SectionsPagerAdapter(
            this,
            supportFragmentManager
        )

        view_pager.adapter = sectionPagerAdapter
        tabs.setupWithViewPager(view_pager)

    }

    private fun setUpToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f
    }


}
