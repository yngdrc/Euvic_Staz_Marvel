package com.euvic.euvic_staz_marvel.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Url(
    @Expose
    @SerializedName("type")
    val type: String,

    @Expose
    @SerializedName("url")
    val url: String
)