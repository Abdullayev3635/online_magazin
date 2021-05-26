package com.abdullayev.onlinemagazin.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abdullayev.onlinemagazin.model.ProductModel

@Dao
interface ProductDao {

    @Query("Delete from products")
    fun deleteAll()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<ProductModel>)

    @Query("select * from products")
    fun getAllProducts(): List<ProductModel>
}