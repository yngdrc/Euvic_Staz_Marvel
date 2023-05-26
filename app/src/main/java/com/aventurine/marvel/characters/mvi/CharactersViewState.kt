package com.aventurine.marvel.characters.mvi

import com.aventurine.marvel.utils.ViewStateBase
import com.aventurine.marvel.db.models.characters.CharactersDataClass

data class CharactersViewState(
    var loading: Boolean = false,
    var characters: CharactersDataClass? = null,
    var error: Throwable? = null,
    var navigate: Int? = null
) : ViewStateBase