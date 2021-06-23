package com.dicodingsub.herryprasetyo.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dicodingsub.herryprasetyo.R
import com.dicodingsub.herryprasetyo.ui.favorite.FavActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpToolbar()
        val sectionPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager
            )
        view_pager.adapter = sectionPagerAdapter
        tabs.setupWithViewPager(view_pager)

    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_favorite) {
            Intent(this, FavActivity::class.java).apply {
                startActivity(this)
            }
        }
        return super.onOptionsItemSelected(item)

    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}
