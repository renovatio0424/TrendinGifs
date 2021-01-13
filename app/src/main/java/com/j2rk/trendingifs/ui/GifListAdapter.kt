package com.j2rk.trendingifs.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.j2rk.trendingifs.R
import com.j2rk.trendingifs.data.db.model.GiphyGif

class GifListAdapter : RecyclerView.Adapter<GifListAdapter.TrendingViewHolder>() {
    private val giphyGifList: ArrayList<GiphyGif> = arrayListOf()

    var onItemClick: ((giphyGif: GiphyGif, position: Int) -> Unit)? = null
    val lastOffset
        get() = if (giphyGifList.isNotEmpty()) giphyGifList.last().offset
        else 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trending_gifs, parent, false)
        return TrendingViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.onBind(giphyGifList[position], position, onItemClick)
    }

    override fun getItemCount() = giphyGifList.size

    fun updateAll(updateList: List<GiphyGif>) {
        val lastIndex = giphyGifList.lastIndex
        giphyGifList.addAll(updateList)
        notifyItemRangeInserted(lastIndex, updateList.size)
    }

    class TrendingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivThumbnail = view.findViewById<ImageView>(R.id.ivGifThumbnail)
        private val cbFavorite = view.findViewById<CheckBox>(R.id.cbFavorite)
        private val tvTitle = view.findViewById<TextView>(R.id.tvTitle)

        fun onBind(giphyGif: GiphyGif, position: Int, onItemClick: ((giphyGif: GiphyGif, position: Int) -> Unit)?) {
            Glide.with(itemView)
                .load(giphyGif.thumbnailUrl)
                .into(ivThumbnail)

            cbFavorite.isChecked = giphyGif.isFavorite

            cbFavorite.setOnClickListener {
                giphyGif.isFavorite = cbFavorite.isChecked
                onItemClick?.invoke(giphyGif, position)
            }

            tvTitle.text = giphyGif.title
        }
    }
}
