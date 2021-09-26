package com.euvic.euvic_staz_marvel.db.models.series

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "series")
data class SeriesResult(
//    @Expose
//    @SerializedName("characters")
//    val characters: Characters,

//    @Expose
//    @SerializedName("comics")
//    val comics: Comics,

//    @Expose
//    @SerializedName("creators")
//    val creators: Creators,

    @Expose
    @SerializedName("description")
    val description: String,

    @Expose
    @SerializedName("endYear")
    val endYear: Int,

//    @Expose
//    @SerializedName("events")
//    val events: Events,

    @Expose
    @SerializedName("id")
    @PrimaryKey
    val id: Int,

    @Expose
    @SerializedName("modified")
    val modified: String,

//    @Expose
//    @SerializedName("next")
//    val next: Any,
//
//    @Expose
//    @SerializedName("previous")
//    val previous: Any,

    @Expose
    @SerializedName("rating")
    val rating: String,

    @Expose
    @SerializedName("resourceURI")
    val resourceURI: String,

    @Expose
    @SerializedName("startYear")
    val startYear: Int,

//    @Expose
//    @SerializedName("stories")
//    val stories: Stories,

    @Expose
    @SerializedName("thumbnail")
    val thumbnail: SeriesThumbnail,

    @Expose
    @SerializedName("title")
    val title: String,

    @Expose
    @SerializedName("type")
    val type: String,

//    @Expose
//    @SerializedName("urls")
//    val urls: List<Url>
)