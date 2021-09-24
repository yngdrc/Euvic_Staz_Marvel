package com.euvic.euvic_staz_marvel.details.mvi

import com.euvic.euvic_staz_marvel.utils.ReducerBase

class DetailsReducer: ReducerBase<DetailsViewState, PartialDetailsState> {

    override fun reduce(previousState: DetailsViewState, changedState: PartialDetailsState): DetailsViewState {
        val newState = previousState.copy()
        when(changedState) {
            is PartialDetailsState.Loading -> {
                newState.loading = true
                newState.details = null
                newState.series = null
                newState.error = null
            }
            is PartialDetailsState.ReceivedDetails -> {
                newState.loading = false
                newState.details = (changedState as PartialDetailsState.ReceivedDetails).details
                newState.error = null
            }
            is PartialDetailsState.ReceivedSeries -> {
                newState.loading = false
                newState.series = (changedState as PartialDetailsState.ReceivedSeries).series
                newState.error = null
            }
            is PartialDetailsState.Error -> {
                newState.loading = false
                newState.details = null
                newState.series = null
                newState.error = changedState.error
            }

        }
        return newState
    }
}