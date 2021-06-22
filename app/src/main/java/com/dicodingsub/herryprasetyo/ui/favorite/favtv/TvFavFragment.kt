package com.dicodingsub.herryprasetyo.ui.favorite.favtv

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
import com.dicodingsub.herryprasetyo.data.source.local.entity.TvEntity
import com.dicodingsub.herryprasetyo.ui.favorite.detail.DetailFavMovieTvActivity
import com.dicodingsub.herryprasetyo.ui.home.HomeActivity
import com.dicodingsub.herryprasetyo.ui.home.tvshow.ItemTvListCallback
import com.dicodingsub.herryprasetyo.ui.home.tvshow.TvPagedAdapter
import com.dicodingsub.herryprasetyo.util.gone
import com.dicodingsub.herryprasetyo.util.visible
import com.dicodingsub.herryprasetyo.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.progress_bar
import kotlinx.android.synthetic.main.fragment_tv_show.*


class TvFavFragment : Fragment(),
    ItemTvListCallback {

    private val tvPagedAdapter = TvPagedAdapter(this)
    private lateinit var viewModel: TvFavViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(recycler_view_tv_shows)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(this, factory)[TvFavViewModel::class.java]
            val movies = viewModel.getTvs()
            loading()
            with(recycler_view_tv_shows) {
                val layoutManager = GridLayoutManager(context, 2)
                this.layoutManager = layoutManager
                setHasFixedSize(true)
                this.adapter = tvPagedAdapter
            }
            movies.observe(viewLifecycleOwner, Observer(this@TvFavFragment::handleData))

        }
    }

    private fun loading() {
        recycler_view_tv_shows.gone()
        progress_bar.visible()
        layout_error_movie_detail.gone()
    }

    private fun handleData(data: PagedList<TvEntity>) {
        recycler_view_tv_shows.visible()
        progress_bar.gone()
        tvPagedAdapter.submitList(data)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == REQ_DETAIL && resultCode == RESULT_DELETED) {
                val entity = data.getParcelableExtra<TvEntity>(DetailFavMovieTvActivity.EXTRA_DATA)
                entity?.let {
                    viewModel.deleteTv(entity)
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
                val movieEntity = tvPagedAdapter.getItemByPos(swipedPosition)
                movieEntity?.let { viewModel.deleteTv(it) }
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

    override fun onItemTvClicked(data: TvEntity) {
        Intent(context, DetailFavMovieTvActivity::class.java).apply {
            putExtra(HomeActivity.EXTRA_ID, data.id)
            putExtra(DetailFavMovieTvActivity.EXTRA_TYPE, DetailFavMovieTvActivity.TYPE_TV)
            startActivityForResult(this, REQ_DETAIL)
        }
    }
}
