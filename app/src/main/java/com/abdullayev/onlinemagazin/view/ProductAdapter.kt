package com.abdullayev.onlinemagazin.view

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdullayev.onlinemagazin.R
import com.abdullayev.onlinemagazin.model.ProductModel
import com.abdullayev.onlinemagazin.screen.productDetall.ProductActivity
import com.abdullayev.onlinemagazin.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.product_item.view.*

class ProductAdapter(val items: List<ProductModel>): RecyclerView.Adapter<ProductAdapter.ItemHolder>(){
    class ItemHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener{
            val intent = Intent(it.context, ProductActivity::class.java)
            intent.putExtra(Constants.PRODUCT_INFO, item)
            it.context.startActivity(intent)
        }
        holder.itemView.txtTitle.text = item.name
        holder.itemView.txtPrice.text = item.price + " сум"
        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE + item.image).into(holder.itemView.image_product);
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}