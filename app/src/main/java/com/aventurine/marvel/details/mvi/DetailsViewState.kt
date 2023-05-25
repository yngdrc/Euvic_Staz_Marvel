package com.aventurine.marvel.details.mvi

import com.aventurine.marvel.utils.ViewStateBase
import com.aventurine.marvel.db.models.characters.CharactersDataClass
import com.aventurine.marvel.db.models.series.SeriesDataClass

data class DetailsViewState(
    var loading: Boolean = false,
    var details: CharactersDataClass? = null,
    var series: SeriesDataClass? = null,
    var error: Throwable? = null
) : ViewStateBase