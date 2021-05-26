package com.abdullayev.onlinemagazin.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullayev.onlinemagazin.MainViewModel
import com.abdullayev.onlinemagazin.R
import com.abdullayev.onlinemagazin.model.CategoryModel
import com.abdullayev.onlinemagazin.model.OfferModel
import com.abdullayev.onlinemagazin.utils.Constants
import com.abdullayev.onlinemagazin.view.CategoryAdapter
import com.abdullayev.onlinemagazin.view.CategoryAdapterCallback
import com.abdullayev.onlinemagazin.view.ProductAdapter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    var offers: List<OfferModel> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe.setOnRefreshListener {
            loadData()
        }
        recycle_categories.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL,false)
        recycle_products.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)

        loadData()

        viewModel.progress.observe(requireActivity(), Observer {
            swipe.isRefreshing = it
        })
        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.offersData.observe(requireActivity(), Observer {
            carouselView.setImageListener { position, imageView ->
                Glide.with(imageView)
                    .load(Constants.HOST_IMAGE + it[position].image)
                    .into(imageView)
            }
            carouselView.pageCount = it.count()
        })

        viewModel.categoryData.observe(requireActivity(), Observer {
            recycle_categories.adapter = CategoryAdapter(it, object: CategoryAdapterCallback{
                override fun onClickItem(item: CategoryModel) {
                    viewModel.getProductsByCategory(item.id)
                }
            })
        })

        viewModel.productData.observe(requireActivity(), Observer {
            recycle_products.adapter = ProductAdapter(it)
        })

    }
    fun loadData(){
        viewModel.getOffers()
        viewModel.getAllDBCategories()
        viewModel.getAllDBProducts()
//        viewModel.getCategories()
//        viewModel.getProducts()
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}