package com.euvic.euvic_staz_marvel.db.models.characters.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ItemSeries(
    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("resourceURI")
    val resourceURI: String
)