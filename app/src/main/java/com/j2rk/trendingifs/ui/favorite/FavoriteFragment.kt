package com.j2rk.trendingifs.ui.favorite

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.j2rk.trendingifs.R
import com.j2rk.trendingifs.ui.GifListAdapter
import com.j2rk.trendingifs.ui.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FavoriteFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvFavorite).apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }

            adapter = GifListAdapter().apply {
                onItemClick = { giphyGif, position ->
                    Handler(Looper.getMainLooper()).post { notifyItemChanged(position) }
                    mainViewModel.updateGiphy(giphyGif)
                }
            }
        }

        mainViewModel.giphyGifList.observe(viewLifecycleOwner, {
            recyclerView.adapter.let { adapter ->
                if (adapter is GifListAdapter) {
                    adapter.updateAll(it.filter { it.isFavorite })
                }
            }
        })

        mainViewModel.errorMessage.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

    }
}