package com.euvic.euvic_staz_marvel.db

import com.euvic.euvic_staz_marvel.db.CharactersDao
import com.euvic.euvic_staz_marvel.db.models.characters.CharactersData
import com.euvic.euvic_staz_marvel.db.models.characters.CharactersDataClass
import com.euvic.euvic_staz_marvel.db.models.characters.CharactersResult

open class CharactersRepo(private val charactersDao: CharactersDao) {
    fun insertCharacters(characters: CharactersResult) =
        charactersDao.insert(characters)

    fun updateCharacters(characters: CharactersResult) =
        charactersDao.update(characters)

    fun deleteCharacters(characters: CharactersResult) =
        charactersDao.delete(characters)

    fun getAllCharacters(): MutableList<CharactersResult> {
        return charactersDao.getCharacters()
    }
}