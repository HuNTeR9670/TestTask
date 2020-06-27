package com.example.testtask.data.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testtask.data.model.Cats

@Dao
interface FeaturedCatsDAO {

    @Query("SELECT * from featured_cats ORDER BY id ASC")
    fun getFeaturedCats(): LiveData<List<Cats>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFeaturedCats (cats: Cats)
}