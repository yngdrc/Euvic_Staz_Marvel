package com.euvic.euvic_staz_marvel.db

import com.euvic.euvic_staz_marvel.db.models.characters.dto.CharactersResultDTO

open class CharactersRepo(private val charactersDao: CharactersDao) {
    fun insertCharacters(characters: CharactersResultDTO) =
        charactersDao.insert(characters)

    fun updateCharacters(characters: CharactersResultDTO) =
        charactersDao.update(characters)

    fun deleteCharacters(characters: CharactersResultDTO) =
        charactersDao.delete(characters)

    fun getAllCharacters(): MutableList<CharactersResultDTO> {
        return charactersDao.getCharacters()
    }
}