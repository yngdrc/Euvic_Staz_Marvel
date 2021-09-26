package com.euvic.euvic_staz_marvel.db.models.series

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemEvents>,
    val returned: Int
)