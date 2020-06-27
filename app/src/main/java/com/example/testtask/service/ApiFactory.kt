package com.example.testtask.service

import com.example.testtask.AppConstants

object ApiFactory {
    val CAT_API_MOVIE: CatApi = RetrofitFactory.retrofit(AppConstants.CAT_BASE_URL)
        .create(CatApi::class.java)
}