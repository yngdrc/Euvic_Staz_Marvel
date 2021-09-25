package com.euvic.euvic_staz_marvel.db.models.characters

import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CharactersDataClass(
    val code: Int,
    val data: CharactersData,
    val status: String
)