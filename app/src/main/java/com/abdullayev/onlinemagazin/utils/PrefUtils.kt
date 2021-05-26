package com.abdullayev.onlinemagazin.utils

import com.abdullayev.onlinemagazin.model.CartModel
import com.abdullayev.onlinemagazin.model.ProductModel
import com.orhanobut.hawk.Hawk
import java.util.ArrayList

object PrefUtils {
    const val PREF_FAVORITES = "Pref_Favorites"
    const val PREF_CART = "Pref_Cart"
    const val PREF_FMC_TOKEN = "Pref_fcm_token"

    fun setFavorite(item: ProductModel){
        val items = Hawk.get(PREF_FAVORITES, arrayListOf<Int>())

        if (items.filter { it == item.id }.firstOrNull() != null){
            items.remove(item.id)
        } else {
            items.add(item.id)
        }
        Hawk.put(PREF_FAVORITES, items)
    }

    fun getFavorites():ArrayList<Int>{
        return Hawk.get(PREF_FAVORITES, arrayListOf<Int>())
    }
    fun checkFavorite(item: ProductModel):Boolean{
        val items = Hawk.get(PREF_FAVORITES, arrayListOf<Int>())
        return items.filter { it == item.id }.firstOrNull() != null;
    }

    fun setCart(item: ProductModel){
        val items = Hawk.get<ArrayList<CartModel>>(PREF_CART, arrayListOf())
        val cart = items.filter { it.product_id == item.id }.firstOrNull()
        if (cart!=null){
            if (item.cartCount>0){
                 cart.product_count = item.cartCount
            } else{
                items.remove(cart)
            }
        } else {
            val newCart = CartModel(item.id, item.cartCount)
            items.add(newCart)
        }
        Hawk.put(PREF_CART, items)
    }

    fun getCartList():ArrayList<CartModel>{
        return Hawk.get(PREF_CART, arrayListOf<CartModel>())
    }

    fun getCartCount(item: ProductModel):Int {
        val items = Hawk.get<ArrayList<CartModel>>(PREF_CART, arrayListOf())
        return items.filter { it.product_id == item.id }.firstOrNull()?.product_count ?: 0
    }

    fun setFMCToken(value: String){
        Hawk.put(PREF_FMC_TOKEN, value)
    }

    fun getFMCToken(): String{
        return Hawk.get(PREF_FMC_TOKEN, "")
    }

}