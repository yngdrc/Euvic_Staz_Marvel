package com.euvic.euvic_staz_marvel.models.series

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemStories>,
    val returned: Int
)