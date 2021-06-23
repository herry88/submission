package com.dicodingsub.herryprasetyo.ui.detail.movie

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicodingsub.herryprasetyo.R
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.ui.home.HomeActivity.Companion.EXTRA_ID
import com.dicodingsub.herryprasetyo.util.convertDateFormat
import com.dicodingsub.herryprasetyo.util.gone
import com.dicodingsub.herryprasetyo.util.loadImageFromUrl
import com.dicodingsub.herryprasetyo.util.visible
import com.dicodingsub.herryprasetyo.viewmodel.ViewModelFactory
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.fortyfor.*

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailMovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        setUpToolbar()
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[DetailMovieViewModel::class.java]
        if (!isNetworkAvailable(this)) {
            layout_error_movie_detail.visible()
            progress_bar.gone()
            Toast.makeText(
                this, getString(R.string.check_connection), Toast.LENGTH_LONG
            ).show()
        }
        loading()
        getData()
        setUpListener()


    }


    private fun getData() {
        val extras = intent.extras
        extras?.apply {
            extras.getString(EXTRA_ID)?.let { id ->
                viewModel.setMovieId(id)
                val data = viewModel.movie
                data.observe(
                    this@DetailMovieActivity,
                    Observer(this@DetailMovieActivity::handleDataMovie)
                )
                viewModel.movieFav.observe(
                    this@DetailMovieActivity,
                    Observer(this@DetailMovieActivity::handleFavMovieState)
                )
            }
        }
    }

    private fun handleFavMovieState(data: MovieEntity?) {
        if (data != null) {
            fab_fav.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite
                )
            )
        } else {
            fab_fav.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_border
                )
            )
        }
    }


    private fun handleDataMovie(data: MovieEntity?) {
        if (data != null) populateView(data) else {
            progress_bar.gone()
            layout_error_no_content_movie.visible()
            Toast.makeText(this, getString(R.string.data_not_found), Toast.LENGTH_LONG).show()
        }
    }


    private fun populateView(data: MovieEntity) {
        progress_bar.gone()
        scroll_view.visible()
        image_postertes.visible()
        image_background.visible()
        tv_title_movie.text = data.title
        tv_desc_movie.text = if (data.description.isNullOrEmpty()) "-" else data.description
        tv_release_date_movie.text = data.release?.convertDateFormat()
        tv_rating_movie.text = data.score
        tv_genres_movie.text = data.genre

        data.poster?.let {
            image_postertes.loadImageFromUrl(it)
        }
    }

    private fun loading() {
        progress_bar.visible()
        layout_error_movie_detail.gone()
        layout_error_no_content_movie.gone()
        scroll_view.gone()
        image_postertes.gone()
        image_background.gone()
    }

    private fun setUpListener() {
        btn_retry.setOnClickListener {
            if (!isNetworkAvailable(this)) {
                Toast.makeText(
                    this, getString(R.string.check_connection), Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            loading()
            getData()
        }
        fab_fav.setOnClickListener {
            viewModel.setFavMovie()
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
                collapsingtoolbar.title = getString(R.string.title_detail_movie)
                isShow = true
            } else if (isShow) {
                collapsingtoolbar.title = " "
                isShow = false
            }
        })
    }


    @Suppress("DEPRECATION")
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}
