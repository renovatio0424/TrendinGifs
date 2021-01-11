package com.j2rk.trendingifs.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.j2rk.trendingifs.R
import com.j2rk.trendingifs.network.BASE_URL_GIPHY
import com.j2rk.trendingifs.network.GiphyService
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private val retrofit: GiphyService = Retrofit.Builder()
        .baseUrl(BASE_URL_GIPHY)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GiphyService::class.java)

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = root.findViewById<RecyclerView>(R.id.rvTrendingList).apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = TrendingListAdapter()
        }

        return root
    }

    override fun onStart() {
        super.onStart()
        Log.d("Main", "on start")
        retrofit.getTrendingGifs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Main", "response: $it")
                (recyclerView.adapter as TrendingListAdapter).addAllTrendingList(it.data)
            }, {
                Log.e("Main", "error: $it")
            })
    }
}