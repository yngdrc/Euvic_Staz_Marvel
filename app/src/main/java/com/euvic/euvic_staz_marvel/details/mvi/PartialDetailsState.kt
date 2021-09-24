package com.euvic.euvic_staz_marvel.details.mvi

import com.euvic.euvic_staz_marvel.utils.ViewStateChangeBase
import com.euvic.euvic_staz_marvel.models.characters.CharactersDataClass
import com.euvic.euvic_staz_marvel.models.series.SeriesDataClass

sealed class PartialDetailsState : ViewStateChangeBase {
    data class Loading(val isLoading: Boolean) : PartialDetailsState()
    data class ReceivedDetails(val details: CharactersDataClass): PartialDetailsState()
    data class ReceivedSeries(val series: SeriesDataClass): PartialDetailsState()
    data class Error(val error: Throwable) : PartialDetailsState()
}