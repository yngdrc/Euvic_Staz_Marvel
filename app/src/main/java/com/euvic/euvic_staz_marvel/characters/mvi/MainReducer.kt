package com.euvic.euvic_staz_marvel.characters.mvi

import com.euvic.euvic_staz_marvel.main.ReducerBase

class MainReducer: ReducerBase<MainViewState, PartialMainState> {

    override fun reduce(previousState: MainViewState, changedState: PartialMainState): MainViewState {
        val newState = previousState.copy()
        when(changedState) {
            is PartialMainState.Loading -> {
                newState.loading = true
                newState.characters = null
                newState.foundCharacters = null
                newState.error = null
            }
            is PartialMainState.GotCharacters -> {
                newState.loading = false
                newState.characters = (changedState as PartialMainState.GotCharacters).characters
                newState.foundCharacters = null
                newState.error = null
            }
            is PartialMainState.FoundCharacters -> {
                newState.loading = false
                newState.characters = null
                newState.foundCharacters = (changedState as PartialMainState.FoundCharacters).foundCharacters
                newState.error = null
            }
            is PartialMainState.Error -> {
                newState.loading = false
                newState.characters = null
                newState.foundCharacters = null
                newState.error = changedState.error
            }

        }
        return newState
    }
}