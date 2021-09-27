package com.euvic.euvic_staz_marvel.db.models.characters.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//DTO a w bazie DAO
@Entity(tableName = "characters")
data class CharactersResultDAO(
//    @Expose
//    @SerializedName("comics")
//    val comics: Comics,

    @Expose
    val description: String,

    @Expose
    @PrimaryKey()
    val id: Int,

    @Expose
    val name: String,

    @Expose
    val resourceURI: String,

//    @Expose
//    @SerializedName("series")
//    val series: Series,

    @Expose
    val thumbnail: CharactersThumbnail,

//    @Expose
//    @SerializedName("urls")
//    val urls: MutableList<Url>?
)