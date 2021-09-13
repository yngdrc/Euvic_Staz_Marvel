package com.euvic.euvic_staz_marvel.apiservice

import com.euvic.euvic_staz_marvel.utils.Constants
import com.euvic.euvic_staz_marvel.models.CharactersDataClass
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceMarvel {
    @GET("characters")
    fun getCharacters(
        @Query("ts") ts: String = Constants.TIMESTAMP,
        @Query("apikey") apiKey: String = Constants.API_PUBLIC_KEY,
        @Query("hash") hash: String = Constants.hash(),
        @Query("limit") limit: String = Constants.RESULTS_LIMIT.toString()
    ): Observable<CharactersDataClass>

    @GET("characters/{id}")
    fun getCharacterById(
        @Path("id") id: Int,
        @Query("ts") ts: String = Constants.TIMESTAMP,
        @Query("apikey") apiKey: String = Constants.API_PUBLIC_KEY,
        @Query("hash") hash: String = Constants.hash(),
        @Query("limit") limit: String = Constants.RESULTS_LIMIT.toString()
    ): Observable<CharactersDataClass>
}