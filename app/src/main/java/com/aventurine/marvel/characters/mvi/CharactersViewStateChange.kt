package com.aventurine.marvel.characters.mvi

import com.aventurine.marvel.utils.ViewStateChangeBase
import com.aventurine.marvel.db.models.characters.CharactersDataClass

sealed class CharactersViewStateChange : ViewStateChangeBase {
    data class Loading(val isLoading: Boolean) : CharactersViewStateChange()
    data class CharactersFetched(
        val characters: CharactersDataClass
    ) : CharactersViewStateChange()

    data class Error(val error: Throwable) : CharactersViewStateChange()

    data class Navigate(
        val characterId: Int
    ) : CharactersViewStateChange()
}