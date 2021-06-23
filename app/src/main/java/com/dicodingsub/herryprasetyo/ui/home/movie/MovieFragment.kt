package com.dicodingsub.herryprasetyo.ui.home.movie

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
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.dicodingsub.herryprasetyo.R
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.ui.detail.movie.DetailMovieActivity
import com.dicodingsub.herryprasetyo.ui.home.HomeActivity
import com.dicodingsub.herryprasetyo.util.gone
import com.dicodingsub.herryprasetyo.util.visible
import com.dicodingsub.herryprasetyo.viewmodel.MovieViewModelFactory
import com.dicodingsub.herryprasetyo.vo.NetworkState
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.fortyfor.*
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.layout_error_movie_detail
import kotlinx.android.synthetic.main.fragment_movie.progress_bar


class MovieFragment : Fragment(),
    ItemMovieListCallback {

    private val movieAdapter = MoviePagedAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setUpRecyclerView()
            val factory = MovieViewModelFactory.getInstance(requireContext())
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
            val movies = viewModel.getMovies()
            if (!isNetworkAvailable(requireContext())) {
                layout_error_movie_detail.visible()
                progress_bar.gone()
                Toast.makeText(
                    requireContext(),
                    getString(R.string.check_connection),
                    Toast.LENGTH_LONG
                ).show()
            }

            loading()
            movies.observe(viewLifecycleOwner, Observer(this@MovieFragment::handleData))
            btn_retry.setOnClickListener {
                if (!isNetworkAvailable(requireContext())) {
                    Toast.makeText(
                        requireContext(), getString(R.string.check_connection), Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
                loading()
                viewModel.getMovies()
                    .observe(viewLifecycleOwner, Observer(this@MovieFragment::handleData))
            }

            viewModel.networkState?.observe(this, Observer {
                movieAdapter.setNetworkState(it)
                if (it.status == NetworkState.Status.SUCCESS) {
                    progress_bar?.visibility = View.GONE
                    layout_error_movie_detail?.visibility = View.GONE
                }
                if (it.status == NetworkState.Status.FAILED) {
                    progress_bar?.visibility = View.GONE
                    layout_error_movie_detail?.visibility = View.VISIBLE
                }
                if (it.status == NetworkState.Status.EMPTY) {
                    progress_bar?.visibility = View.GONE
                }
            })

        }
    }

    private fun setUpRecyclerView() {
        with(recycler_view_movies) {
            val layoutManager = GridLayoutManager(context, 2)
            layoutManager.spanSizeLookup =
                object : SpanSizeLookup() {
                    override fun getSpanSize(pos: Int): Int {
                        return when (movieAdapter.getItemViewType(pos)) {
                            MoviePagedAdapter.TYPE_PROGRESS -> 2
                            MoviePagedAdapter.TYPE_ITEM -> 1
                            else -> -1
                        }
                    }
                }
            this.layoutManager = layoutManager
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun loading() {
        recycler_view_movies.gone()
        progress_bar.visible()
        layout_error_movie_detail.gone()
    }

    private fun handleData(data: PagedList<MovieEntity>) {
        recycler_view_movies.visible()
        progress_bar.gone()
        movieAdapter.submitList(data)
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

    override fun onItemMovieClicked(data: MovieEntity) {
        Intent(context, DetailMovieActivity::class.java).apply {
            putExtra(HomeActivity.EXTRA_ID, data.id)
            startActivity(this)

        }
    }
}
