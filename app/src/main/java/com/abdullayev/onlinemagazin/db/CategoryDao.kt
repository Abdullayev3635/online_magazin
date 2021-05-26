package com.abdullayev.onlinemagazin.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abdullayev.onlinemagazin.model.CategoryModel

@Dao

interface  CategoryDao{

    @Query("Delete from categories")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<CategoryModel>)

    @Query("select * from categories")
    fun getAllCategories(): List<CategoryModel>
}
