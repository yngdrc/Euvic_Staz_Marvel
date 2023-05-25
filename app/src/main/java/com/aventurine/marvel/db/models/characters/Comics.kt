package com.aventurine.marvel.db.models.characters

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Comics(
    @Expose
    @SerializedName("available")
    val available: Int,

    @Expose
    @SerializedName("collectionURI")
    val collectionURI: String,

    @Expose
    @SerializedName("items")
    val items: MutableList<ItemComics>,

    @Expose
    @SerializedName("returned")
    val returned: Int
)