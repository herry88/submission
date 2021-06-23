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
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.dicodingsub.herryprasetyo.R
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import com.dicodingsub.herryprasetyo.ui.detail.tv.DetailTvActivity
import com.dicodingsub.herryprasetyo.ui.home.HomeActivity
import com.dicodingsub.herryprasetyo.util.gone
import com.dicodingsub.herryprasetyo.util.visible
import com.dicodingsub.herryprasetyo.viewmodel.TvViewModelFactory
import com.dicodingsub.herryprasetyo.vo.NetworkState
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.android.synthetic.main.fortyfor.*

class TvShowFragment : Fragment(),
    ItemTvListCallback {

    private val tvShowAdapter = TvPagedAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setUpRecyclerView()
            val factory = TvViewModelFactory.getInstance(requireContext())
            val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]
            val tvShows = viewModel.getTVShows()

            if (!isNetworkAvailable(requireContext())) {
                layout_error.visible()
                progress_bar.gone()
                Toast.makeText(
                    requireContext(), getString(R.string.check_connection), Toast.LENGTH_LONG
                ).show()
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

            viewModel.networkState?.observe(viewLifecycleOwner, Observer {
                tvShowAdapter.setNetworkState(it)
                if (it.status == NetworkState.Status.SUCCESS) {
                    progress_bar?.visibility = View.GONE
                    layout_error?.visibility = View.GONE
                }
                if (it.status == NetworkState.Status.FAILED) {
                    progress_bar?.visibility = View.GONE
                    layout_error?.visibility = View.VISIBLE
                }
                if (it.status == NetworkState.Status.EMPTY) {
                    progress_bar?.visibility = View.GONE
                }
            })
        }


    }

    private fun setUpRecyclerView() {
        with(recycler_view_tv_shows) {
            val layoutManager = GridLayoutManager(context, 2)
            layoutManager.spanSizeLookup =
                object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(pos: Int): Int {
                        return when (tvShowAdapter.getItemViewType(pos)) {
                            TvPagedAdapter.TYPE_PROGRESS -> 2
                            TvPagedAdapter.TYPE_ITEM -> 1
                            else -> -1
                        }
                    }
                }
            this.layoutManager = layoutManager
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }
    }

    private fun loading() {
        recycler_view_tv_shows.gone()
        progress_bar.visible()
        layout_error.gone()
    }

    private fun handleData(data: PagedList<TvEntity>) {
        recycler_view_tv_shows.visible()
        progress_bar.gone()
        tvShowAdapter.submitList(data)
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


    override fun onItemTvClicked(data: TvEntity) {
        Intent(context, DetailTvActivity::class.java).apply {
            putExtra(HomeActivity.EXTRA_ID, data.id)
            startActivity(this)
        }
    }

}
