package com.abdullayev.onlinemagazin.api

import com.abdullayev.onlinemagazin.model.BaseResponse
import com.abdullayev.onlinemagazin.model.CategoryModel
import com.abdullayev.onlinemagazin.model.OfferModel
import com.abdullayev.onlinemagazin.model.ProductModel
import com.abdullayev.onlinemagazin.model.request.GetProductsByIdsRequest
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @GET("get_offers")
    fun getOffers(): Observable<BaseResponse<List<OfferModel>>>

    @GET("get_categories")
    fun getCategories(): Observable<BaseResponse<List<CategoryModel>>>

    @GET("get_top_products")
    fun getProduct(): Observable<BaseResponse<List<ProductModel>>>

    @GET("get_products/{category_id}")
    fun getCategoryProduct(@Path("category_id") categoryId: Int): Observable<BaseResponse<List<ProductModel>>>

    @POST("get_products_by_ids")
    fun getProductByIds(@Body request: GetProductsByIdsRequest): Observable<BaseResponse<List<ProductModel>>>

}
