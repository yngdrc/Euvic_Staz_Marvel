package com.euvic.euvic_staz_marvel.apiservice

import com.euvic.euvic_staz_marvel.models.characters.CharactersDataClass
import com.euvic.euvic_staz_marvel.models.series.SeriesDataClass
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

    fun getSeriesByCharacterId(characterId: Int): Observable<SeriesDataClass> {
        return RetrofitBuilder.apiService.getComicsByCharacterId(characterId)
    }
}