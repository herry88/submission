package com.dicodingsub.herryprasetyo.ui.detail

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.dicodingsub.herryprasetyo.R
import com.dicodingsub.herryprasetyo.model.MovieEntity
import com.dicodingsub.herryprasetyo.util.gone
import com.dicodingsub.herryprasetyo.util.loadImageFromUrl
import com.dicodingsub.herryprasetyo.util.visible
import com.dicodingsub.herryprasetyo.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.fortyfor.*
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        setUpToolbar()
        viewModel =
            ViewModelProvider(this, ViewModelFactory.getInstance())[DetailViewModel::class.java]
        if (!isNetworkAvailable(this)) {
            layout_error.visible()
            progress_bar.gone()
            Toast.makeText(
                this, getString(R.string.check_connection), Toast.LENGTH_LONG
            ).show()
        }
        loading()
        getData()
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


    }

    private fun getData() {
        val extras = intent.extras
        extras?.apply {
            extras.getString(EXTRA_ID)?.let { id ->
                when (extras.getInt(EXTRA_TYPE, 0)) {
                    TYPE_MOVIE -> {
                        viewModel.setMovieId(id)
                        val data = viewModel.getMovieById()
                        data.observe(this@DetailActivity, Observer(this@DetailActivity::handleData))
                    }
                    TYPE_TVSHOW -> {
                        viewModel.setTvShowId(id)
                        val data = viewModel.getTvShowById()
                        data.observe(this@DetailActivity, Observer(this@DetailActivity::handleData))
                    }
                }
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
                intent.extras?.let { extras ->
                    when (extras.getInt(EXTRA_TYPE, 0)) {
                        TYPE_MOVIE -> {
                            collapsingtoolbar.title = getString(R.string.title_detail_movie)
                        }
                        TYPE_TVSHOW -> {
                            collapsingtoolbar.title = getString(R.string.title_detail_tvshow)
                        }
                    }
                }
                isShow = true
            } else if (isShow) {
                collapsingtoolbar.title = " "
                isShow = false
            }
        })
    }

    private fun handleData(data: MovieEntity?) {
        if (data != null) populateView(data) else {
            progress_bar.gone()
            layout_error_no_content.visible()
            Toast.makeText(this, getString(R.string.data_not_found), Toast.LENGTH_LONG).show()
        }
    }

    private fun populateView(data: MovieEntity) {
        progress_bar.gone()
        scroll_view.visible()
        image_poster.visible()
        image_background.visible()
        tv_title.text = data.title
        tv_desc.text = if (data.description.isNullOrEmpty()) "-" else data.description
        data.release?.let {
            val dateRelease = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it)
            dateRelease?.apply {
                val dateReleaseFormatted =
                    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(this)
                data.release = dateReleaseFormatted
                tv_release_date.text = data.release
            }

        }

        tv_rating.text = data.score
        tv_genres.text = data.genre

        data.poster?.let {
            image_poster.loadImageFromUrl(it)
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


    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TYPE = "extra_type"
        const val TYPE_MOVIE = 1
        const val TYPE_TVSHOW = 2
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
