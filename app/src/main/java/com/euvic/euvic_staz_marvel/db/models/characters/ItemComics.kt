package com.euvic.euvic_staz_marvel.db.models.characters

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ItemComics(
    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("resourceURI")
    val resourceURI: String
)