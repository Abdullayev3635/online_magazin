package com.abdullayev.onlinemagazin

import android.text.BoringLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdullayev.onlinemagazin.api.repository.MainRepository
import com.abdullayev.onlinemagazin.db.AppDatabase
import com.abdullayev.onlinemagazin.model.CategoryModel
import com.abdullayev.onlinemagazin.model.OfferModel
import com.abdullayev.onlinemagazin.model.ProductModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    val repository = MainRepository()

    val error = MutableLiveData<String>()

    val progress = MutableLiveData<Boolean>()

    val offersData = MutableLiveData<List<OfferModel>>()

    val categoryData = MutableLiveData<List<CategoryModel>>()

    val productData = MutableLiveData<List<ProductModel>>()

    fun getOffers(){
        repository.getOffers(error, progress, offersData)
    }

    fun getCategories(){
        repository.getCategories(error, categoryData)
    }

    fun getProducts(){
        repository.getProducts(error, productData)
    }

    fun getProductsByCategory(id: Int){
        repository.getProductsByCategory(id, error, productData)
    }

    fun getProductsByIds(ids: List<Int>){
        repository.getProductsByIds(ids, error, progress, productData)
    }

    fun insertAllProducts2DB(items: List<ProductModel>){
        CoroutineScope(Dispatchers.IO).launch{
            AppDatabase.getDatabase().getProductDao().deleteAll()
            AppDatabase.getDatabase().getProductDao().insertAll(items)
        }
    }

    fun insertAllCategory2DB(items: List<CategoryModel>){
        CoroutineScope(Dispatchers.IO).launch{
            AppDatabase.getDatabase().getCategoryDao().deleteAll()
            AppDatabase.getDatabase().getCategoryDao().insertAll(items)
        }
    }

    fun getAllDBProducts(){
        CoroutineScope(Dispatchers.Main).launch {
            productData.value = withContext(Dispatchers.IO){AppDatabase.getDatabase().getProductDao().getAllProducts()}
        }
    }
    fun getAllDBCategories(){
        CoroutineScope(Dispatchers.Main).launch {
            categoryData.value = withContext(Dispatchers.IO){AppDatabase.getDatabase().getCategoryDao().getAllCategories()}
        }
    }

}