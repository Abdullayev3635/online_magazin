package com.abdullayev.onlinemagazin.api

import com.abdullayev.onlinemagazin.utils.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    var reteofit: Retrofit? = null

    var api: Api? = null

    fun getApiService():Api {
        if (api == null){
            reteofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constants.HOST)
                .build()
            api = reteofit!!.create(Api::class.java)
        }
        return api!!
    }

}