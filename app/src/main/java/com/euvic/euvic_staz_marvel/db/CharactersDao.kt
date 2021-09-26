package com.euvic.euvic_staz_marvel.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.euvic.euvic_staz_marvel.db.models.characters.CharactersData
import com.euvic.euvic_staz_marvel.db.models.characters.CharactersDataClass
import com.euvic.euvic_staz_marvel.db.models.characters.CharactersResult

@Dao
interface CharactersDao {
    @Insert
    fun insert(characters: CharactersResult)

    @Update
    fun update(characters: CharactersResult)

    @Delete
    fun delete(characters: CharactersResult)

    @Query("SELECT * FROM characters")
    fun getCharacters(): MutableList<CharactersResult>

    @RawQuery
    fun getCharactersViaQuery(query: SupportSQLiteQuery): MutableList<CharactersResult>
}