package com.aventurine.marvel.db.models.characters

import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CharactersResult(
    @Expose
    @SerializedName("comics")
    val comics: Comics,

    @Expose
    @SerializedName("description")
    val description: String,

    @Expose
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
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
    val thumbnail: CharactersThumbnail,

    @Expose
    @SerializedName("urls")
    val urls: MutableList<Url>?
)