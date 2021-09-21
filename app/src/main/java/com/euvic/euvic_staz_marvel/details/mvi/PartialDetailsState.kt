package com.euvic.euvic_staz_marvel.details.mvi

import com.euvic.euvic_staz_marvel.main.ViewStateChangeBase
import com.euvic.euvic_staz_marvel.models.CharactersDataClass
import com.euvic.euvic_staz_marvel.models.Result

sealed class PartialDetailsState : ViewStateChangeBase {
    data class Loading(val isLoading: Boolean) : PartialDetailsState()
    data class ReceivedDetails(val details: CharactersDataClass): PartialDetailsState()
    data class ReceivedEmpty(val empty: Int): PartialDetailsState()
    data class Error(val error: Throwable) : PartialDetailsState()
}