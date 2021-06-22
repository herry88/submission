package com.dicodingsub.herryprasetyo.ui.favorite.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.dicodingsub.herryprasetyo.R
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import com.dicodingsub.herryprasetyo.ui.favorite.favmovie.MovieFavFragment

import com.dicodingsub.herryprasetyo.ui.home.HomeActivity.Companion.EXTRA_ID
import com.dicodingsub.herryprasetyo.util.gone
import com.dicodingsub.herryprasetyo.util.loadImageFromUrl
import com.dicodingsub.herryprasetyo.util.visible
import com.dicodingsub.herryprasetyo.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_tv.*

class DetailFavMovieTvActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailFavMovieTvViewModel
    private var typeData: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)
        setUpToolbar()
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[DetailFavMovieTvViewModel::class.java]
        loading()
        getData()
        setUpListener()


    }


    private fun getData() {
        val extras = intent.extras
        extras?.apply {
            extras.getString(EXTRA_ID)?.let { id ->
                typeData = extras.getInt(EXTRA_TYPE, 0)
                if (typeData == 1) {
                    viewModel.setMovieId(id)
                    val data = viewModel.movieFav
                    data.observe(
                        this@DetailFavMovieTvActivity,
                        Observer(this@DetailFavMovieTvActivity::handleDataMovie)
                    )
                } else {
                    viewModel.setTvShowId(id)
                    val data = viewModel.tvFav
                    data.observe(
                        this@DetailFavMovieTvActivity,
                        Observer(this@DetailFavMovieTvActivity::handleDataMovie)
                    )
                }
            }
        }
    }


    private fun handleDataMovie(data: MovieEntity?) {
        if (data != null) populateView(data as Any) else {
            progress_bar.gone()
            layout_error_no_content.visible()
            Toast.makeText(this, getString(R.string.data_not_found), Toast.LENGTH_LONG).show()
        }
    }

    private fun handleDataMovie(data: TvEntity?) {
        if (data != null) populateView(data as Any) else {
            progress_bar.gone()
            layout_error_no_content.visible()
            Toast.makeText(this, getString(R.string.data_not_found), Toast.LENGTH_LONG).show()
        }
    }

    private fun populateView(data: Any) {
        fab_fav.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_favorite_border
            )
        )
        progress_bar.gone()
        scroll_view.visible()
        image_poster.visible()
        image_background.visible()

        if (typeData == 1) {
            val dataMovie = data as MovieEntity
            tv_title.text = dataMovie.title
            tv_desc.text = if (dataMovie.description.isNullOrEmpty()) "-" else dataMovie.description
            tv_release_date.text = dataMovie.release
            tv_rating.text = dataMovie.score
            tv_genres.text = dataMovie.genre
            dataMovie.poster?.let {
                image_poster.loadImageFromUrl(it)
            }
        } else {
            val dataTv = data as TvEntity
            tv_title.text = dataTv.title
            tv_desc.text = if (dataTv.description.isNullOrEmpty()) "-" else dataTv.description
            tv_release_date.text = dataTv.release
            tv_rating.text = dataTv.score
            tv_genres.text = dataTv.genre
            dataTv.poster?.let {
                image_poster.loadImageFromUrl(it)
            }
        }

    }

    private fun loading() {
        progress_bar.visible()
        layout_error.gone()
        layout_error_no_content.gone()
        scroll_view.gone()
        image_poster.gone()
        image_background.gone()
    }

    private fun setUpListener() {
        fab_fav.setOnClickListener {
            Intent().apply {
                this.putExtra(
                    EXTRA_DATA,
                    if (typeData == 1) viewModel.movieFav.value else viewModel.tvFav.value
                )
                setResult(MovieFavFragment.RESULT_DELETED, this)
                finish()
            }
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var isShow = true
        var scrollRange = -1
        app_bar_layout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                if (typeData == 1) {
                    collapsingtoolbar.title = getString(R.string.title_detail_movie_fav)
                } else {
                    collapsingtoolbar.title = getString(R.string.title_detail_tvshow_fav)
                }
                isShow = true
            } else if (isShow) {
                collapsingtoolbar.title = " "
                isShow = false
            }
        })
    }


    companion object {
        const val EXTRA_TYPE = "exstra_typedata"
        const val EXTRA_DATA = "exstra_data"
        const val TYPE_MOVIE = 1
        const val TYPE_TV = 2

    }


}
