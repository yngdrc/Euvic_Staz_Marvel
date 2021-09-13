package com.euvic.euvic_staz_marvel.main

import android.util.Log
import com.euvic.euvic_staz_marvel.main.PartialMainState.Loading
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter() : MviBasePresenter<MainView, MainViewState>() {

    override fun bindIntents() {
        val getCharacters: Observable<PartialMainState> = intent(MainView::getCharacters)
            .map { characters ->
                Log.d("RESULT", characters.toString())
                PartialMainState.GotCharacters(characters) as PartialMainState
            }
            .startWith(Loading())
            .onErrorReturn { error -> PartialMainState.Error(error) }
            .subscribeOn(Schedulers.io())

        val mainViewState: MainViewState = MainViewState(false, null, null)
        val allIntents: Observable<PartialMainState> = getCharacters
            .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntents.scan(mainViewState, this::viewStateReducer), MainView::render)
    }

    fun viewStateReducer(
        previousState: MainViewState,
        changedStatePart: PartialMainState
    ): MainViewState {
        val newState = previousState

        if (changedStatePart is Loading) {
            newState.loading = true
        }

        if (changedStatePart is PartialMainState.GotCharacters) {
            newState.loading = false
            newState.characters = (changedStatePart as PartialMainState.GotCharacters).characters
        }

        if (changedStatePart is PartialMainState.Error) {
            newState.loading = false
            newState.error = changedStatePart.error
        }
        return newState
    }

}