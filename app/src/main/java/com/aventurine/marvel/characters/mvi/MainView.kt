package com.aventurine.marvel.characters.mvi

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.rxjava3.core.Observable

interface MainView : MvpView {
    val getCharacters: Observable<Int>
    val searchCharacters: Observable<CharSequence>
    fun render(viewState: MainViewState)
}