package com.dicodingsub.herryprasetyo.ui.detail.tv

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
import com.google.android.material.appbar.AppBarLayout
import com.dicodingsub.herryprasetyo.R
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity

import com.dicodingsub.herryprasetyo.ui.home.HomeActivity.Companion.EXTRA_ID
import com.dicodingsub.herryprasetyo.util.convertDateFormat
import com.dicodingsub.herryprasetyo.util.gone
import com.dicodingsub.herryprasetyo.util.loadImageFromUrl
import com.dicodingsub.herryprasetyo.util.visible
import com.dicodingsub.herryprasetyo.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_tv.*
import kotlinx.android.synthetic.main.fortyfor.*

class DetailTvActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailTvViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)
        setUpToolbar()
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[DetailTvViewModel::class.java]
        if (!isNetworkAvailable(this)) {
            layout_error.visible()
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
                viewModel.setTvShowId(id)
                val data = viewModel.tv
                data.observe(
                    this@DetailTvActivity,
                    Observer(this@DetailTvActivity::handleDataMovie)
                )
                viewModel.tvFav.observe(
                    this@DetailTvActivity,
                    Observer(this@DetailTvActivity::handleFavTvState)
                )
            }
        }
    }


    private fun handleFavTvState(data: TvEntity?) {
        if (data != null) {
            fab_fav.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_border
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

    private fun handleDataMovie(data: TvEntity?) {
        if (data != null) populateView(data) else {
            progress_bar.gone()
            layout_error_no_content.visible()
            Toast.makeText(this, getString(R.string.data_not_found), Toast.LENGTH_LONG).show()
        }
    }


    private fun populateView(data: TvEntity) {
        progress_bar.gone()
        scroll_view.visible()
        image_poster.visible()
        image_background.visible()
        tv_title.text = data.title
        tv_desc.text = if (data.description.isNullOrEmpty()) "-" else data.description
        tv_release_date.text = data.release?.convertDateFormat()
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
            viewModel.setFavTv()
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
                collapsingtoolbar.title = getString(R.string.title_detail_tvshow)
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
