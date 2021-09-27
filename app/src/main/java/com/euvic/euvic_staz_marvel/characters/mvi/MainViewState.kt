package com.euvic.euvic_staz_marvel.characters.mvi

import com.euvic.euvic_staz_marvel.utils.ViewStateBase
import com.euvic.euvic_staz_marvel.db.models.characters.dto.CharactersResultDTO

data class MainViewState(
    var loading: Boolean = false,
    var characters: MutableList<CharactersResultDTO>? = null,
    var foundCharacters: MutableList<CharactersResultDTO>? = null,
    var error: Throwable? = null
) : ViewStateBase