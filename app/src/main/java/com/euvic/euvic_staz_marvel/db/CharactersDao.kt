package com.euvic.euvic_staz_marvel.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.euvic.euvic_staz_marvel.db.models.characters.dto.CharactersResultDTO

@Dao
interface CharactersDao {
    @Insert
    fun insert(characters: CharactersResultDTO)

    @Update
    fun update(characters: CharactersResultDTO)

    @Delete
    fun delete(characters: CharactersResultDTO)

    @Query("SELECT * FROM characters")
    fun getCharacters(): MutableList<CharactersResultDTO>

    @RawQuery
    fun getCharactersViaQuery(query: SupportSQLiteQuery): MutableList<CharactersResultDTO>
}