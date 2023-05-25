package com.aventurine.marvel.utils

interface ReducerBase<T: ViewStateBase, T2: ViewStateChangeBase> {
    fun reduce(previousState: T, changedState: T2): T
}