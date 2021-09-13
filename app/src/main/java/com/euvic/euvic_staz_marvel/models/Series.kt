package com.euvic.euvic_staz_marvel.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Series(
    @Expose
    @SerializedName("available")
    val available: Int,

    @Expose
    @SerializedName("collectionURI")
    val collectionURI: String,

    @Expose
    @SerializedName("items")
    val items: List<ItemSeries>,

    @Expose
    @SerializedName("returned")
    val returned: Int
)