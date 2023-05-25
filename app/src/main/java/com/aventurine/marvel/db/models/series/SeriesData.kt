package com.aventurine.marvel.db.models.series

data class SeriesData(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: MutableList<SeriesResult>,
    val total: Int
)