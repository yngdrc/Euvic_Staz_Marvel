package com.euvic.euvic_staz_marvel.models.series

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: MutableList<SeriesResult>,
    val total: Int
)