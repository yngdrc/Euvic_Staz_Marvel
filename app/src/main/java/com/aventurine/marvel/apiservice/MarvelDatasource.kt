package com.aventurine.marvel.apiservice

import com.aventurine.marvel.db.models.characters.CharactersDataClass
import com.aventurine.marvel.db.models.series.SeriesDataClass
import io.reactivex.rxjava3.core.Observable

class MarvelDatasource {
    fun getCharacters(offset: Int): Observable<CharactersDataClass> =
        RetrofitBuilder.apiService.getCharacters(offset = offset)

    fun getDetails(characterId: Int): Observable<CharactersDataClass> =
        RetrofitBuilder.apiService.getCharacterById(id = characterId)

    fun searchCharacters(searchText: CharSequence): Observable<CharactersDataClass> =
        RetrofitBuilder.apiService.searchCharacters(searchText = searchText)

    fun getSeriesByCharacterId(characterId: Int): Observable<SeriesDataClass> =
        RetrofitBuilder.apiService.getComicsByCharacterId(characterId = characterId)
}