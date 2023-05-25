package com.aventurine.marvel.characters.mvi

import com.aventurine.marvel.utils.ViewStateChangeBase
import com.aventurine.marvel.db.models.characters.CharactersDataClass

sealed class PartialMainState : ViewStateChangeBase {
    data class Loading(val isLoading: Boolean) : PartialMainState()
    data class GotCharacters(val characters: CharactersDataClass) : PartialMainState()
    data class FoundCharacters(val foundCharacters: CharactersDataClass) : PartialMainState()
    data class Error(val error: Throwable) : PartialMainState()
}