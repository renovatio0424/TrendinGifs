package com.j2rk.trendingifs.ui.home

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

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<RecyclerView>(R.id.rvTrendingList).apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }


            adapter = GifListAdapter().apply {
                onItemClick = { giphyGif, position ->
                    Handler(Looper.getMainLooper()).post { notifyItemChanged(position) }
                    mainViewModel.updateGiphy(giphyGif)
                }
            }

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisibleItemPosition =
                        (recyclerView.layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(
                            null
                        ).maxByOrNull { it } ?: 0
                    val lastIndex = recyclerView.adapter?.itemCount ?: 0
                    val loadIndex = lastIndex - 10

                    val nextOffset = (recyclerView.adapter as GifListAdapter).lastOffset + 1

                    if (lastVisibleItemPosition == loadIndex) {
                        mainViewModel.loadNextGiphyList(nextOffset)
                    }
                }
            })
        }

        mainViewModel.giphyGifList.observe(viewLifecycleOwner, {
            recyclerView.adapter.let { adapter ->
                if (adapter is GifListAdapter) {
                    if (adapter.lastOffset != it[0].offset || adapter.lastOffset == 0)
                        adapter.updateAll(it)
                }
            }
        })

        mainViewModel.errorMessage.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }
}