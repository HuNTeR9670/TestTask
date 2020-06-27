package com.example.testtask.data.dataBase

import android.content.Context
import androidx.room.*
import com.example.testtask.data.model.Cats

@Database(entities = [Cats::class], version = 1, exportSchema = false)
abstract class FeaturedCatsDatabase : RoomDatabase(){

    abstract fun featuredCatsDAO(): FeaturedCatsDAO

    companion object{
        @Volatile
        private var INSTANCE: FeaturedCatsDatabase? = null

        fun getDatabase(context: Context): FeaturedCatsDatabase? {
            if (INSTANCE == null) {
                synchronized(FeaturedCatsDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            FeaturedCatsDatabase::class.java, "cats_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}