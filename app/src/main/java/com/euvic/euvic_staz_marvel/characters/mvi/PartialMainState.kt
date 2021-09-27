package com.euvic.euvic_staz_marvel.characters.mvi

import com.euvic.euvic_staz_marvel.utils.ViewStateChangeBase
import com.euvic.euvic_staz_marvel.db.models.characters.dto.CharactersResultDTO

sealed class PartialMainState : ViewStateChangeBase {
    data class Loading(val isLoading: Boolean) : PartialMainState()
    data class GotCharacters(val characters: MutableList<CharactersResultDTO>) : PartialMainState()
    data class FoundCharacters(val foundCharacters: MutableList<CharactersResultDTO>) : PartialMainState()
    data class Error(val error: Throwable) : PartialMainState()
}