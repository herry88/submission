package com.dicodingsub.herryprasetyo.ui.home.tvshow

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dicodingsub.herryprasetyo.R
import com.dicodingsub.herryprasetyo.model.MovieEntity
import com.dicodingsub.herryprasetyo.ui.detail.DetailActivity
import com.dicodingsub.herryprasetyo.ui.home.ItemListCallback
import com.dicodingsub.herryprasetyo.util.gone
import com.dicodingsub.herryprasetyo.util.visible
import com.dicodingsub.herryprasetyo.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.android.synthetic.main.fortyfor.*

class TvShowFragment : Fragment(), ItemListCallback {

    private val tvShowAdapter = TvShowAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val factory = ViewModelFactory.getInstance()
            val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]
            val tvShows = viewModel.getTVShows()

            if (!isNetworkAvailable(requireContext())) {
                layout_error.visible()
                progress_bar.gone()
                Toast.makeText(
                    requireContext(), getString(R.string.check_connection), Toast.LENGTH_LONG
                ).show()
            }
            with(recycler_view_tv_shows) {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = tvShowAdapter
            }
            loading()
            tvShows.observe(viewLifecycleOwner, Observer(this@TvShowFragment::handleData))
            btn_retry.setOnClickListener {
                if (!isNetworkAvailable(requireContext())) {
                    Toast.makeText(
                        requireContext(), getString(R.string.check_connection), Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
                loading()
                viewModel.getTVShows()
                    .observe(viewLifecycleOwner, Observer(this@TvShowFragment::handleData))
            }
        }
    }

    private fun loading() {
        recycler_view_tv_shows.gone()
        progress_bar.visible()
        layout_error.gone()
    }

    private fun handleData(data: List<MovieEntity>) {
        recycler_view_tv_shows.visible()
        progress_bar.gone()
        tvShowAdapter.setData(data)
        tvShowAdapter.notifyDataSetChanged()
    }


    override fun onItemCardClicked(data: MovieEntity) {
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.TYPE_TVSHOW)
            putExtra(DetailActivity.EXTRA_ID, data.id)
        }
        startActivity(intent)

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
