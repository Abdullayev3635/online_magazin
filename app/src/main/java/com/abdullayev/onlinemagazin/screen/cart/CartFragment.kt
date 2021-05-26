package com.abdullayev.onlinemagazin.screen.cart

import android.content.Intent
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
import com.abdullayev.onlinemagazin.model.ProductModel
import com.abdullayev.onlinemagazin.screen.makeorder.MakeOrderActivity
import com.abdullayev.onlinemagazin.utils.Constants
import com.abdullayev.onlinemagazin.utils.PrefUtils
import com.abdullayev.onlinemagazin.view.CartAdapter
import kotlinx.android.synthetic.main.fragment_cart.*
import java.io.Serializable

class CartFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.progress.observe(this, Observer {
            swipe_cart.isRefreshing = it
        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(requireActivity(), it,Toast.LENGTH_LONG).show()
        })
        viewModel.productData.observe(this, Observer {
            recycle_cart.adapter = CartAdapter(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycle_cart.layoutManager = LinearLayoutManager(requireActivity())
        swipe_cart.setOnRefreshListener {
            loadData()
        }
        loadData()
        btnMakeOrder.setOnClickListener {
            val intent = Intent(requireActivity(), MakeOrderActivity::class.java)
            intent.putExtra(Constants.PRODUCT_INFO, (viewModel.productData.value?: emptyList<ProductModel>()) as Serializable)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = CartFragment()
    }

    fun loadData(){
        viewModel.getProductsByIds(PrefUtils.getCartList().map { it.product_id })
    }
}