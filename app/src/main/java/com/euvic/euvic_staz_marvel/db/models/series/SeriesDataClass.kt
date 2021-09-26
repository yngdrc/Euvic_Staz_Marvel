package com.euvic.euvic_staz_marvel.db.models.series

data class SeriesDataClass(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val data: SeriesData,
    val etag: String,
    val status: String
)