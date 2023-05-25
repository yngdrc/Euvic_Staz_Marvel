package com.aventurine.marvel.db.models.series

import com.aventurine.marvel.db.models.series.SeriesData

data class SeriesDataClass(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: SeriesData,
    val etag: String,
    val status: String
)