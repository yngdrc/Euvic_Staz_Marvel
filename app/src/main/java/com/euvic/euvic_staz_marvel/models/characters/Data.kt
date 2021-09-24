package com.euvic.euvic_staz_marvel.models.characters

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class  Data(
    @Expose
    @SerializedName("count")
    val count: Int,

    @Expose
    @SerializedName("limit")
    val limit: Int,

    @Expose
    @SerializedName("offset")
    val offset: Int,

    @Expose
    @SerializedName("results")
    val results: MutableList<CharactersResult>,

    @Expose
    @SerializedName("total")
    val total: Int
)