package com.j2rk.trendingifs.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.j2rk.trendingifs.R
import com.j2rk.trendingifs.network.model.Data

class TrendingListAdapter : RecyclerView.Adapter<TrendingListAdapter.TrendingViewHolder>() {
    private var trendingGifList: ArrayList<Data> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trending_gifs, parent, false)
        return TrendingViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.onBind(trendingGifList[position])
    }

    override fun getItemCount() = trendingGifList.size

    fun addAllTrendingList(trendingList: List<Data>) {
        val startPosition = trendingGifList.lastIndex + 1
        trendingGifList.addAll(trendingList)
        notifyItemRangeInserted(startPosition, trendingList.size)
    }

    class TrendingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivThumbnail = view.findViewById<ImageView>(R.id.ivGifThumbnail)

        fun onBind(trendingGifItem: Data) {
            Glide.with(itemView)
                .load(trendingGifItem.images.fixed_width.url)
                .into(ivThumbnail)
        }
    }
}
