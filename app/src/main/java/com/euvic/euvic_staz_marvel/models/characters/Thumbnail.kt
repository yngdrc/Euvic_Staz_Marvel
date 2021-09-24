package com.euvic.euvic_staz_marvel.models.characters

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @Expose
    @SerializedName("extension")
    val extension: String,

    @Expose
    @SerializedName("path")
    val path: String
)