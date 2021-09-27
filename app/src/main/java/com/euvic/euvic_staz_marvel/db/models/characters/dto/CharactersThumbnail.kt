package com.euvic.euvic_staz_marvel.db.models.characters.dto

import androidx.room.Entity
import androidx.room.ForeignKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "charactersThumbnail", foreignKeys = [ForeignKey(
    entity = CharactersResultDTO::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("id"),
    onDelete = ForeignKey.CASCADE
)]
)
data class CharactersThumbnail(
    @Expose
    @SerializedName("extension")
    val extension: String,

    @Expose
    @SerializedName("path")
    val path: String,

    @Expose
    @SerializedName("id")
    val id: Int
)