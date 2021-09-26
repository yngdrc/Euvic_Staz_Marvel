package com.euvic.euvic_staz_marvel.characters.mvi

import com.euvic.euvic_staz_marvel.utils.ViewStateBase
import com.euvic.euvic_staz_marvel.db.models.characters.CharactersDataClass
import com.euvic.euvic_staz_marvel.db.models.characters.CharactersResult

data class MainViewState(
    var loading: Boolean = false,
    var characters: MutableList<CharactersResult>? = null,
    var foundCharacters: MutableList<CharactersResult>? = null,
    var error: Throwable? = null
) : ViewStateBase