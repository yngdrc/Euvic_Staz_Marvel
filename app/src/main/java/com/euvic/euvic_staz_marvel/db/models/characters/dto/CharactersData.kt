package com.euvic.euvic_staz_marvel.db.models.characters.dto

data class  CharactersData(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: MutableList<CharactersResultDTO>,
    val total: Int
)