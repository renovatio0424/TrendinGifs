package com.j2rk.trendingifs.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import com.j2rk.trendingifs.R
import com.j2rk.trendingifs.data.db.AppDatabase

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDatabase = Room.databaseBuilder(view.context, AppDatabase::class.java, "TrendingGifDb").build()
        homeViewModel =
            ViewModelProvider(this, HomeViewModelFactory(appDatabase)).get(HomeViewModel::class.java)

        recyclerView = view.findViewById<RecyclerView>(R.id.rvTrendingList).apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = TrendingListAdapter(homeViewModel.giphyGifList)
        }

        homeViewModel.giphyGifList.observe(viewLifecycleOwner, {
            recyclerView.adapter.let {
                if (it is TrendingListAdapter)
                    it.updateAll()
            }
        })
    }
}