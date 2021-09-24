package com.euvic.euvic_staz_marvel.characters.mvi

import com.euvic.euvic_staz_marvel.utils.ViewStateBase
import com.euvic.euvic_staz_marvel.models.characters.CharactersDataClass

data class MainViewState(
    var loading: Boolean = false,
    var characters: CharactersDataClass? = null,
    var foundCharacters: CharactersDataClass? = null,
    var error: Throwable? = null
) : ViewStateBase