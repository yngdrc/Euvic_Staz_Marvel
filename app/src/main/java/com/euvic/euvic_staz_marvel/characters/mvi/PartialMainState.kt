package com.euvic.euvic_staz_marvel.characters.mvi

import com.euvic.euvic_staz_marvel.main.ViewStateChangeBase
import com.euvic.euvic_staz_marvel.models.CharactersDataClass

sealed class PartialMainState : ViewStateChangeBase {
    data class Loading(val isLoading: Boolean) : PartialMainState()
    data class GotCharacters(val characters: CharactersDataClass) : PartialMainState()
    data class FoundCharacters(val foundCharacters: CharactersDataClass) : PartialMainState()
    data class Error(val error: Throwable) : PartialMainState()
}