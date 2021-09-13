package com.euvic.euvic_staz_marvel.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CharactersDataClass(
    @Expose
    @SerializedName("code")
    val code: Int,

    @Expose
    @SerializedName("data")
    val data: Data,

    @Expose
    @SerializedName("status")
    val status: String
)