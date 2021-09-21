package com.euvic.euvic_staz_marvel.characters.mvi

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface MainView : MvpView {
    val getCharacters: Observable<Int>
    val searchCharacters: Observable<CharSequence>
    fun render(viewState: MainViewState)
}