package com.aventurine.marvel.characters.mvi

import com.aventurine.marvel.utils.ReducerBase

class CharactersViewStateReducer : ReducerBase<CharactersViewState, CharactersViewStateChange> {

    override fun reduce(
        previousState: CharactersViewState,
        changedState: CharactersViewStateChange
    ): CharactersViewState = previousState.copy().apply {
        when (changedState) {
            is CharactersViewStateChange.CharactersFetched -> {
                characters = changedState.characters
                navigate = null
            }

            is CharactersViewStateChange.Navigate -> {
                navigate = changedState.characterId
            }

            else -> {}
        }
    }
}