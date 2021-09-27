package com.euvic.euvic_staz_marvel.db.models.characters.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//DTO a w bazie DAO
@Entity(tableName = "characters")
data class CharactersResultDTO(
    @Expose
    @SerializedName("comics")
    val comics: Comics,

    @Expose
    @SerializedName("description")
    val description: String,

    @Expose
    @SerializedName("id")
    @PrimaryKey()
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