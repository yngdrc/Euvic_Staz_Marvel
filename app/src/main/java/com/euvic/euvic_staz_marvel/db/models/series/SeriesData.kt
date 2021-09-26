package com.euvic.euvic_staz_marvel.db.models.series

import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SeriesData(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: MutableList<SeriesResult>,
    val total: Int
)