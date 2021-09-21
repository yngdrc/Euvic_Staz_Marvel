package com.euvic.euvic_staz_marvel.main

import com.euvic.euvic_staz_marvel.characters.mvi.PartialMainState

interface ReducerBase<T: ViewStateBase, T2: ViewStateChangeBase> {
    fun reduce(previousState: T, changedState: T2): T
}