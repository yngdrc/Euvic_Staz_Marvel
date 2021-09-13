package com.euvic.euvic_staz_marvel.apiservice

import com.euvic.euvic_staz_marvel.utils.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.MARVEL_API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: ApiServiceMarvel by lazy {
        retrofitBuilder
            .build()
            .create(ApiServiceMarvel::class.java)
    }
}