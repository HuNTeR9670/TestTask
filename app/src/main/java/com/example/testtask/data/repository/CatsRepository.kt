package com.example.testtask.data.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.testtask.data.dataBase.FeaturedCatsDAO
import com.example.testtask.data.dataBase.FeaturedCatsDatabase
import com.example.testtask.data.model.Cats
import com.example.testtask.service.ApiFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CatsRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var featuredCatsDAO: FeaturedCatsDAO?

    private var catsList: MutableLiveData<ArrayList<Cats>> = MutableLiveData()


    init {
        val db = FeaturedCatsDatabase.getDatabase(application)
        featuredCatsDAO = db?.featuredCatsDAO()
    }


    fun getFeatureCats() = featuredCatsDAO?.getFeaturedCats()

    fun addFeatureCats(cats: Cats) {
        launch { addFeatureCatsBG(cats) }
    }

    private suspend fun addFeatureCatsBG(cats: Cats) {
        withContext(Dispatchers.IO) {
            featuredCatsDAO?.addFeaturedCats(cats)
        }
    }

    fun getCats(page: Int) : MutableLiveData<ArrayList<Cats>> {
        launch { getCatsBG(page) }
        return catsList
    }

    fun getMoreCats(page: Int): MutableLiveData<ArrayList<Cats>> {
        launch { getMoreCatsBG(page) }
        return catsList
    }

    private suspend fun getCatsBG(page: Int){
        withContext(Dispatchers.Main) {
            val catsResponse = ApiFactory.CAT_API_MOVIE.getAllCats(page)
            if (catsResponse.isSuccessful) {
                catsList.value = catsResponse.body()
            } else {
                catsResponse.errorBody()
            }
        }
    }

    private suspend fun getMoreCatsBG(page: Int)  {
        withContext(Dispatchers.IO) {
            val catsResponse = ApiFactory.CAT_API_MOVIE.getAllCats(page)
            if (catsResponse.isSuccessful) {
                val tempList = catsList.value
                tempList!!.addAll(catsResponse.body()!!)
                catsList.postValue(tempList)
            } else {
                catsResponse.errorBody()
            }
        }
    }
}