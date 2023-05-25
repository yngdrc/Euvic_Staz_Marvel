package com.aventurine.marvel.db.models.series

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemComics>,
    val returned: Int
)