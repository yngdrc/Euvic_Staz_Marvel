package com.aventurine.marvel.details.mvi

import com.aventurine.marvel.utils.ViewStateChangeBase
import com.aventurine.marvel.db.models.characters.CharactersDataClass
import com.aventurine.marvel.db.models.series.SeriesDataClass

sealed class PartialDetailsState : ViewStateChangeBase {
    data class Loading(val isLoading: Boolean) : PartialDetailsState()
    data class ReceivedDetails(val details: CharactersDataClass): PartialDetailsState()
    data class ReceivedSeries(val series: SeriesDataClass): PartialDetailsState()
    data class Error(val error: Throwable) : PartialDetailsState()
}