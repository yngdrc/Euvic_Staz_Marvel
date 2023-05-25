package com.aventurine.marvel.db.models.characters

import com.aventurine.marvel.db.models.characters.ItemSeries
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
    val items: MutableList<ItemSeries>,

    @Expose
    @SerializedName("returned")
    val returned: Int
)