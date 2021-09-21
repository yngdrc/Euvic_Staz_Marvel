package com.euvic.euvic_staz_marvel.apiservice

import com.euvic.euvic_staz_marvel.apiservice.RetrofitBuilder
import com.euvic.euvic_staz_marvel.models.CharactersDataClass
import io.reactivex.Observable

class MarvelDatasource {
    fun getCharacters(offset: Int): Observable<CharactersDataClass> {
        return RetrofitBuilder.apiService.getCharacters(offset)
    }

    fun getDetails(characterId: Int): Observable<CharactersDataClass> {
        return RetrofitBuilder.apiService.getCharacterById(characterId)
    }

    fun searchCharacters(searchText: CharSequence): Observable<CharactersDataClass> {
        return RetrofitBuilder.apiService.searchCharacters(searchText)
    }
}