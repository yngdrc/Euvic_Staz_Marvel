package com.euvic.euvic_staz_marvel.utils

import com.euvic.euvic_staz_marvel.db.models.characters.dto.CharactersResultDAO
import com.euvic.euvic_staz_marvel.db.models.characters.dto.CharactersResultDTO

fun CharactersResultDTO.convertToDao(): CharactersResultDAO {
    val charactersResultDAO = CharactersResultDAO(id = this.id, description = this.description, name = this.name, resourceURI = this.resourceURI)
}