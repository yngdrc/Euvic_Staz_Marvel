package com.euvic.euvic_staz_marvel.characters.mvi

import com.euvic.euvic_staz_marvel.main.ViewStateBase
import com.euvic.euvic_staz_marvel.models.CharactersDataClass

data class MainViewState(
    var loading: Boolean = false,
    var characters: CharactersDataClass? = null,
    var foundCharacters: CharactersDataClass? = null,
    var error: Throwable? = null
) : ViewStateBase