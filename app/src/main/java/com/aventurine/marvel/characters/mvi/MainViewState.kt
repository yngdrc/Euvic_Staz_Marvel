package com.aventurine.marvel.characters.mvi

import com.aventurine.marvel.utils.ViewStateBase
import com.aventurine.marvel.db.models.characters.CharactersDataClass

data class MainViewState(
    var loading: Boolean = false,
    var characters: CharactersDataClass? = null,
    var foundCharacters: CharactersDataClass? = null,
    var error: Throwable? = null
) : ViewStateBase