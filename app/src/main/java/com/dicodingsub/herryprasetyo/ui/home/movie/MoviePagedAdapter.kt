package com.dicodingsub.herryprasetyo.ui.home.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicodingsub.herryprasetyo.R
import com.dicodingsub.herryprasetyo.data.source.local.entity.MovieEntity
import com.dicodingsub.herryprasetyo.util.loadImageFromUrl
import com.dicodingsub.herryprasetyo.vo.NetworkState
import kotlinx.android.synthetic.main.hold_network.view.*
import kotlinx.android.synthetic.main.grid_view.view.*

class MoviePagedAdapter(private val listener: ItemMovieListCallback) :
    PagedListAdapter<MovieEntity, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_PROGRESS) {
            NetworkStateItemViewHolder(
                layoutInflater.inflate(
                    R.layout.hold_network,
                    parent, false
                )
            )
        } else {
            MovieViewHolder(layoutInflater.inflate(R.layout.grid_view, parent, false))

        }
    }


    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = networkState
        val previousExtraRow = hasExtraRow()
        networkState = newNetworkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            TYPE_PROGRESS
        } else {
            TYPE_ITEM
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: MovieEntity) {
            with(itemView) {
                tv_title.text = data.title

                data.poster?.let {
                    imagePoster.loadImageFromUrl(it)
                }
                when {
                    bindingAdapterPosition % 5 == 0 -> {
                        frame_layout.setBackgroundColor(
                            ActivityCompat.getColor(
                                this.context,
                                R.color.colorBackground1
                            )
                        )
                    }
                    bindingAdapterPosition % 5 == 1 -> {
                        frame_layout.setBackgroundColor(
                            ActivityCompat.getColor(
                                this.context,
                                R.color.colorBackground2
                            )
                        )
                    }
                    bindingAdapterPosition % 5 == 2 -> {
                        frame_layout.setBackgroundColor(
                            ActivityCompat.getColor(
                                this.context,
                                R.color.colorBackground3
                            )
                        )
                    }
                    bindingAdapterPosition % 5 == 3 -> {
                        frame_layout.setBackgroundColor(
                            ActivityCompat.getColor(
                                this.context,
                                R.color.colorBackground4
                            )
                        )
                    }
                    bindingAdapterPosition % 5 == 4 -> {
                        frame_layout.setBackgroundColor(
                            ActivityCompat.getColor(
                                this.context,
                                R.color.colorBackground5
                            )
                        )
                    }
                }

                container.setOnClickListener {
                    listener.onItemMovieClicked(data)
                }
            }
        }
    }

    fun getItemByPos(position: Int) = getItem(position)

    inner class NetworkStateItemViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val progressBar: ProgressBar = item.progress_bar
        private val errorMsg: TextView = item.error_msg

        fun bindView(networkState: NetworkState?) {
            if (networkState != null && networkState.status == NetworkState.Status.RUNNING) {
                progressBar.visibility = View.VISIBLE
                errorMsg.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                errorMsg.visibility = View.GONE
            }

            if (networkState != null && networkState.status == NetworkState.Status.EMPTY) {
                errorMsg.visibility = View.VISIBLE
                errorMsg.text = errorMsg.context.getString(R.string.no_more_content)
                progressBar.visibility = View.GONE
            }

            if (networkState != null && networkState.status == NetworkState.Status.FAILED) {
                errorMsg.visibility = View.VISIBLE
                errorMsg.text = networkState.msg
            }
        }
    }


    companion object {
        const val TYPE_PROGRESS = 0
        const val TYPE_ITEM = 1
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            getItem(position)?.let {
                holder.bind(it)
            }
        } else {
            (holder as NetworkStateItemViewHolder).bindView(networkState)
        }
    }
}