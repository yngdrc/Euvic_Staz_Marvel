package com.euvic.euvic_staz_marvel.details.mvi

import com.euvic.euvic_staz_marvel.utils.ViewStateBase
import com.euvic.euvic_staz_marvel.db.models.characters.CharactersDataClass
import com.euvic.euvic_staz_marvel.db.models.series.SeriesDataClass

data class DetailsViewState(
    var loading: Boolean = false,
    var details: CharactersDataClass? = null,
    var series: SeriesDataClass? = null,
    var error: Throwable? = null
) : ViewStateBase