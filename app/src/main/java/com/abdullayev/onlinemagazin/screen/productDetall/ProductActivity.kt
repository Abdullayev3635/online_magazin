package com.abdullayev.onlinemagazin.screen.productDetall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.abdullayev.onlinemagazin.R
import com.abdullayev.onlinemagazin.model.ProductModel
import com.abdullayev.onlinemagazin.utils.Constants
import com.abdullayev.onlinemagazin.utils.PrefUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity() {
    lateinit var item:ProductModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        item = intent.getSerializableExtra(Constants.PRODUCT_INFO) as ProductModel
        card_back.setOnClickListener {
            finish()
        }
        if(PrefUtils.checkFavorite(item)){
            imageFavorite.setImageResource(R.drawable.ic_heart)
        } else{
            imageFavorite.setImageResource(R.drawable.ic_favorite)
        }

        if(PrefUtils.getCartCount(item)>0){
            btnAdd2Cart.visibility = View.GONE
        }
        card_favorite.setOnClickListener {
            PrefUtils.setFavorite(item)
            if(PrefUtils.checkFavorite(item)){
                imageFavorite.setImageResource(R.drawable.ic_heart)
            } else{
                imageFavorite.setImageResource(R.drawable.ic_favorite)
            }
        }
        btnAdd2Cart.setOnClickListener {
            item.cartCount = 1
            PrefUtils.setCart(item)
            Toast.makeText(this, "Product add to cart", Toast.LENGTH_LONG).show()
            finish()
        }
        tvTitle.text = item.name
        tvProductName.text = item.name
        tvProductPrice.text = item.price
        Glide.with(this).load(Constants.HOST_IMAGE + item.image).into(imageProduct)
    }
}