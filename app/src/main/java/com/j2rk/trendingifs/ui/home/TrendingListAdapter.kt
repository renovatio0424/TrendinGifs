package com.j2rk.trendingifs.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.j2rk.trendingifs.R
import com.j2rk.trendingifs.data.db.model.GiphyGif

class TrendingListAdapter(private val data: LiveData<List<GiphyGif>>) : RecyclerView.Adapter<TrendingListAdapter.TrendingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trending_gifs, parent, false)
        return TrendingViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        data.value?.let {
            holder.onBind(it[position])
        }
    }

    override fun getItemCount() = data.value?.size ?: 0

    fun updateAll() {
        notifyDataSetChanged()
    }

    class TrendingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivThumbnail = view.findViewById<ImageView>(R.id.ivGifThumbnail)
        private val cbFavorite = view.findViewById<CheckBox>(R.id.cbFavorite)

        fun onBind(giphyGif: GiphyGif) {
            Glide.with(itemView)
                .load(giphyGif.thumbnailUrl)
                .into(ivThumbnail)

            cbFavorite.isChecked = giphyGif.isFavorite

            cbFavorite.setOnCheckedChangeListener { _, isChecked ->
                giphyGif.isFavorite = isChecked
            }
        }
    }
}
