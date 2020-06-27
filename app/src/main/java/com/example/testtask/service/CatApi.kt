package com.example.testtask.service

import com.example.testtask.data.model.Cats
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET(value = "/v1/images/search")
    suspend fun getAllCats(@Query(value = "page") page: Int,
                           @Query(value = "limit")  limit: Int = 20,
                           @Query(value = "size") size: String = "med")
            : Response<ArrayList<Cats>>

}