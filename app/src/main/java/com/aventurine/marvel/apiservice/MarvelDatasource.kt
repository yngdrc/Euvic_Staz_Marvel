package com.aventurine.marvel.apiservice

import com.aventurine.marvel.db.models.characters.CharactersDataClass
import com.aventurine.marvel.db.models.series.SeriesDataClass
import io.reactivex.rxjava3.core.Observable

class MarvelDatasource() {
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