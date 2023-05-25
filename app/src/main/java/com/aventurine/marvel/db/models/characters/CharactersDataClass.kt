package com.aventurine.marvel.db.models.characters

import com.aventurine.marvel.db.models.characters.CharactersData

data class CharactersDataClass(
    val code: Int,
    val data: CharactersData,
    val status: String
)