package com.euvic.euvic_staz_marvel.main

interface MainReducerBase<T: ViewStateBase, T2: PartialMainState> {
    fun reduce(previousState: T, changedState: T2): T
}