package com.euvic.euvic_staz_marvel.main

import com.euvic.euvic_staz_marvel.models.CharactersDataClass
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface MainView : MvpView {
    val getCharacters: Observable<Int>
    val searchCharacters: Observable<CharSequence>
    fun render(viewState: MainViewState)
}