package com.euvic.euvic_staz_marvel.details.mvi

import com.euvic.euvic_staz_marvel.characters.mvi.MainViewState
import com.euvic.euvic_staz_marvel.characters.mvi.PartialMainState
import com.euvic.euvic_staz_marvel.main.ReducerBase

class DetailsReducer: ReducerBase<DetailsViewState, PartialDetailsState> {

    override fun reduce(previousState: DetailsViewState, changedState: PartialDetailsState): DetailsViewState {
        val newState = previousState.copy()
        when(changedState) {
            is PartialDetailsState.Loading -> {
                newState.loading = true
                newState.details = null
                newState.empty = 0
                newState.error = null
            }
            is PartialDetailsState.ReceivedDetails -> {
                newState.loading = false
                newState.details = (changedState as PartialDetailsState.ReceivedDetails).details
                newState.empty = 0
                newState.error = null
            }
            is PartialDetailsState.ReceivedEmpty -> {
                newState.loading = false
                newState.details = null
                newState.empty = 1
                newState.error = null
            }
            is PartialDetailsState.Error -> {
                newState.loading = false
                newState.details = null
                newState.empty = 0
                newState.error = changedState.error
            }

        }
        return newState
    }
}