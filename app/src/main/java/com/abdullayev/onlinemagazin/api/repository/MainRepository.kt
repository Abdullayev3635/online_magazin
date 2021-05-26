package com.abdullayev.onlinemagazin.api.repository

import androidx.lifecycle.MutableLiveData
import com.abdullayev.onlinemagazin.api.NetworkManager
import com.abdullayev.onlinemagazin.model.BaseResponse
import com.abdullayev.onlinemagazin.model.CategoryModel
import com.abdullayev.onlinemagazin.model.OfferModel
import com.abdullayev.onlinemagazin.model.ProductModel
import com.abdullayev.onlinemagazin.model.request.GetProductsByIdsRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainRepository {

    val compositeDisposable = CompositeDisposable()
    fun getOffers(error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<List<OfferModel>>) {
        progress.value = true
        compositeDisposable.add(
            NetworkManager.getApiService().getOffers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<OfferModel>>>(){
                    override fun onNext(t: BaseResponse<List<OfferModel>>) {
                        progress.value = false
                        if(t.success){
                            success.value = t.data
                        } else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }

    fun getCategories(error: MutableLiveData<String>, success: MutableLiveData<List<CategoryModel>>) {
        compositeDisposable.add(
            NetworkManager.getApiService().getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<CategoryModel>>>(){
                    override fun onNext(t: BaseResponse<List<CategoryModel>>) {

                        if(t.success){
                            success.value = t.data
                        } else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }

    fun getProducts(error: MutableLiveData<String>, success: MutableLiveData<List<ProductModel>>) {
        compositeDisposable.add(
            NetworkManager.getApiService().getProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<ProductModel>>>(){
                    override fun onNext(t: BaseResponse<List<ProductModel>>) {

                        if(t.success){
                            success.value = t.data
                        } else{
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }

    fun getProductsByCategory(id:Int, error: MutableLiveData<String>, success: MutableLiveData<List<ProductModel>>) {
        compositeDisposable.add(
            NetworkManager.getApiService().getCategoryProduct(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<ProductModel>>>(){
                    override fun onNext(t: BaseResponse<List<ProductModel>>) {
                        if(t.success){
                            success.value = t.data
                        } else{
                            error.value = t.message
                        }
                    }
                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }
                    override fun onComplete() {
                    }
                })
        )
    }
    fun getProductsByIds(ids:List<Int>, error: MutableLiveData<String>, progress: MutableLiveData<Boolean>, success: MutableLiveData<List<ProductModel>>) {
        progress.value = true
        compositeDisposable.add(
            NetworkManager.getApiService().getProductByIds(GetProductsByIdsRequest(ids))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<BaseResponse<List<ProductModel>>>(){
                    override fun onNext(t: BaseResponse<List<ProductModel>>) {
                        progress.value = false
                        if(t.success){
                            success.value = t.data
                        } else{
                            error.value = t.message
                        }
                    }
                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                    override fun onComplete() {
                    }
                })
        )
    }
}