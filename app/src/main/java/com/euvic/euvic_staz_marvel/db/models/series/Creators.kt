package com.euvic.euvic_staz_marvel.db.models.series

data class Creators(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemCreators>,
    val returned: Int
)