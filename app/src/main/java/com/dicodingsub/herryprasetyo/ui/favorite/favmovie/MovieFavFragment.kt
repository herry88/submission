package com.dicodingsub.herryprasetyo.ui.favorite.favmovie

import android.content.Intent
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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dicodingsub.herryprasetyo.R
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.ui.favorite.detail.DetailFavMovieTvActivity
import com.dicodingsub.herryprasetyo.ui.home.HomeActivity
import com.dicodingsub.herryprasetyo.ui.home.movie.ItemMovieListCallback
import com.dicodingsub.herryprasetyo.ui.home.movie.MoviePagedAdapter
import com.dicodingsub.herryprasetyo.util.gone
import com.dicodingsub.herryprasetyo.util.visible
import com.dicodingsub.herryprasetyo.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.layout_error_movie_detail
import kotlinx.android.synthetic.main.fragment_movie.progress_bar


class MovieFavFragment : Fragment(),
    ItemMovieListCallback {

    private val movieAdapter = MoviePagedAdapter(this)
    private lateinit var viewModel: MovieFavViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(recycler_view_movies)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(this, factory)[MovieFavViewModel::class.java]
            val movies = viewModel.getMovies()
            loading()
            with(recycler_view_movies) {
                val layoutManager = GridLayoutManager(context, 2)
                this.layoutManager = layoutManager
                setHasFixedSize(true)
                adapter = movieAdapter
            }
            movies.observe(viewLifecycleOwner, Observer(this@MovieFavFragment::handleData))

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


    override fun onItemMovieClicked(data: MovieEntity) {
        Intent(context, DetailFavMovieTvActivity::class.java).apply {
            putExtra(HomeActivity.EXTRA_ID, data.id)
            putExtra(DetailFavMovieTvActivity.EXTRA_TYPE, DetailFavMovieTvActivity.TYPE_MOVIE)
            startActivityForResult(this, REQ_DETAIL)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == REQ_DETAIL && resultCode == RESULT_DELETED) {
                val entity =
                    data.getParcelableExtra<MovieEntity>(DetailFavMovieTvActivity.EXTRA_DATA)
                entity?.let {
                    viewModel.deleteMovie(entity)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.success_delete),
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }
    }


    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieEntity = movieAdapter.getItemByPos(swipedPosition)
                movieEntity?.let { viewModel.deleteMovie(it) }
                Toast.makeText(
                    requireContext(),
                    getString(R.string.success_delete),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    })

    companion object {
        const val REQ_DETAIL = 999
        const val RESULT_DELETED = 989
    }
}
