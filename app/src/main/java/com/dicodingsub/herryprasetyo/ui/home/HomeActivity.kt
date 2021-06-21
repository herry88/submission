package com.dicodingsub.herryprasetyo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicodingsub.herryprasetyo.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
//        setUpToolbar()
        val sectionPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionPagerAdapter
        tabs.setupWithViewPager(view_pager)

    }


}
