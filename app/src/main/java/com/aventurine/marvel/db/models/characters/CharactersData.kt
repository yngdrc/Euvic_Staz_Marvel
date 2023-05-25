package com.aventurine.marvel.db.models.characters

data class  CharactersData(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: MutableList<CharactersResult>,
    val total: Int
)