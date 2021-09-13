package com.euvic.euvic_staz_marvel.apiservice

import com.euvic.euvic_staz_marvel.apiservice.RetrofitBuilder
import com.euvic.euvic_staz_marvel.models.CharactersDataClass
import io.reactivex.Observable

class MarvelDatasource {
    fun getCharacters(): Observable<CharactersDataClass> {
        return RetrofitBuilder.apiService.getCharacters()
    }
}