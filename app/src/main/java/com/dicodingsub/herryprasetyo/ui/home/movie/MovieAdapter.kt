@file:Suppress("DEPRECATION")

package com.dicodingsub.herryprasetyo.ui.home.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.dicodingsub.herryprasetyo.R
import com.dicodingsub.herryprasetyo.model.MovieEntity
import com.dicodingsub.herryprasetyo.ui.home.ItemListCallback
import com.dicodingsub.herryprasetyo.util.loadImageFromUrl
import kotlinx.android.synthetic.main.grid_view.view.*

class MovieAdapter(private val listener: ItemListCallback) :
    RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {

    private val listData = ArrayList<MovieEntity>()

    fun setData(data: List<MovieEntity>?) {
        if (data == null) return
        listData.clear()
        listData.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.grid_view, parent, false)
        )


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: MovieEntity) {
            with(itemView) {
                tv_title.text = data.title

                data.poster?.let {
                    imagePoster.loadImageFromUrl(it)
                }
                when {
                    adapterPosition % 5 == 0 -> {
                        frame_layout.setBackgroundColor(
                            ActivityCompat.getColor(
                                this.context,
                                R.color.colorBackground1
                            )
                        )
                    }
                    adapterPosition % 5 == 1 -> {
                        frame_layout.setBackgroundColor(
                            ActivityCompat.getColor(
                                this.context,
                                R.color.colorBackground2
                            )
                        )
                    }
                    adapterPosition % 5 == 2 -> {
                        frame_layout.setBackgroundColor(
                            ActivityCompat.getColor(
                                this.context,
                                R.color.colorBackground3
                            )
                        )
                    }
                    adapterPosition % 5 == 3 -> {
                        frame_layout.setBackgroundColor(
                            ActivityCompat.getColor(
                                this.context,
                                R.color.colorBackground4
                            )
                        )
                    }
                    adapterPosition % 5 == 4 -> {
                        frame_layout.setBackgroundColor(
                            ActivityCompat.getColor(
                                this.context,
                                R.color.colorBackground5
                            )
                        )
                    }
                }

                container.setOnClickListener {
                    listener.onItemCardClicked(data)
                }
            }
        }
    }
}