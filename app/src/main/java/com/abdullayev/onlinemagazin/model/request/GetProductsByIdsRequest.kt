package com.abdullayev.onlinemagazin.model.request

data class GetProductsByIdsRequest(
    val products: List<Int>
)