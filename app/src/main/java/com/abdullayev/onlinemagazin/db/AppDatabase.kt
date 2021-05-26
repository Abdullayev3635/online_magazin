package com.abdullayev.onlinemagazin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abdullayev.onlinemagazin.model.CategoryModel
import com.abdullayev.onlinemagazin.model.ProductModel

@Database(entities = [CategoryModel::class, ProductModel::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getCategoryDao(): CategoryDao
    companion object{
        var INSTANCE: AppDatabase? = null
        fun initDatabase(context: Context){
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,"online_shop_db").build()
            }
        }

        fun getDatabase():AppDatabase{
            return INSTANCE!!
        }
    }

}