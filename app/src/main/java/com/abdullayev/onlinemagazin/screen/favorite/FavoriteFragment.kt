package com.abdullayev.onlinemagazin.screen.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullayev.onlinemagazin.MainViewModel
import com.abdullayev.onlinemagazin.R
import com.abdullayev.onlinemagazin.utils.PrefUtils
import com.abdullayev.onlinemagazin.view.ProductAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.productData.observe(requireActivity(), Observer {
            recycle_favorite.adapter = ProductAdapter(it)
        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })
        viewModel.progress.observe(this, Observer {
            swipe_favorite.isRefreshing = it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycle_favorite.layoutManager = LinearLayoutManager(requireActivity())
        swipe_favorite.setOnRefreshListener {
            loadData()
        }
        loadData()
    }

    fun loadData(){
        viewModel.getProductsByIds(PrefUtils.getFavorites())
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}