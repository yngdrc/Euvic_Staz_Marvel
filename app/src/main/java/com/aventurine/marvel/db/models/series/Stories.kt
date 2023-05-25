package com.aventurine.marvel.db.models.series

import com.aventurine.marvel.db.models.series.ItemStories

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemStories>,
    val returned: Int
)