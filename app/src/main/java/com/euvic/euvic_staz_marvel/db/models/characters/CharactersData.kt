package com.euvic.euvic_staz_marvel.db.models.characters

import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class  CharactersData(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: MutableList<CharactersResult>,
    val total: Int
)