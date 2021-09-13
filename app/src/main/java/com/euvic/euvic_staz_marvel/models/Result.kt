package com.euvic.euvic_staz_marvel.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
    @Expose
    @SerializedName("comics")
    val comics: Comics,

    @Expose
    @SerializedName("description")
    val description: String,

    @Expose
    @SerializedName("id")
    val id: Int,

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("resourceURI")
    val resourceURI: String,

    @Expose
    @SerializedName("series")
    val series: Series,

    @Expose
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail,

    @Expose
    @SerializedName("urls")
    val urls: List<Url>
)