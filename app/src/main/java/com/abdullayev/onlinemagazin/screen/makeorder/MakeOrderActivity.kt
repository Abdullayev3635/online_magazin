package com.abdullayev.onlinemagazin.screen.makeorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdullayev.onlinemagazin.MapsActivity
import com.abdullayev.onlinemagazin.R
import com.abdullayev.onlinemagazin.model.AddressModel
import com.abdullayev.onlinemagazin.model.ProductModel
import com.abdullayev.onlinemagazin.utils.Constants
import kotlinx.android.synthetic.main.activity_make_order.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MakeOrderActivity : AppCompatActivity() {
    var address: AddressModel? = null
    lateinit var items: List<ProductModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_order)

        items = intent.getSerializableExtra(Constants.PRODUCT_INFO) as List<ProductModel>

        tvTotalPrice.setText(items.sumByDouble { it.cartCount.toDouble() * (it.price.replace(" ", "").toDoubleOrNull() ?:0.0) }.toString())

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }

        card_back.setOnClickListener {
            finish()
        }
        edtAddress.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }
    @Subscribe
    fun onEvent(address: AddressModel){
        this.address = address
        edtAddress.setText("${address.latitude}, ${address.longitude}")
    }
}