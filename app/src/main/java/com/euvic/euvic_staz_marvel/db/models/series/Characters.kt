package com.euvic.euvic_staz_marvel.db.models.series

data class Characters(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemCharacters>,
    val returned: Int
)