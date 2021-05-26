package com.abdullayev.onlinemagazin.model

import java.io.Serializable

data class AddressModel(
    val address: String,
    val latitude: Double,
    val longitude: Double
): Serializable