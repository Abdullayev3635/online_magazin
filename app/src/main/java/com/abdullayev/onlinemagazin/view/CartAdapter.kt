package com.abdullayev.onlinemagazin.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdullayev.onlinemagazin.R
import com.abdullayev.onlinemagazin.model.ProductModel
import com.abdullayev.onlinemagazin.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_item.view.*

class CartAdapter(val items: List<ProductModel>): RecyclerView.Adapter<CartAdapter.ItemHolder>() {
    class ItemHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val  item = items[position]
        holder.itemView.txtPrice.text = item.price
        holder.itemView.txtTitle.text = item.name
        Glide.with(holder.itemView).load(Constants.HOST_IMAGE + item.image).into(holder.itemView.image_cart)
        holder.itemView.tvCount.text = item.cartCount.toString()
    }

    override fun getItemCount(): Int {
        return items.count()
    }

}