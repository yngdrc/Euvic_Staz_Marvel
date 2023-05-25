package com.aventurine.marvel.apiservice

import com.aventurine.marvel.utils.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.MARVEL_API_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: ApiServiceMarvel by lazy {
        retrofitBuilder
            .build()
            .create(ApiServiceMarvel::class.java)
    }
}