package com.euvic.euvic_staz_marvel.main

import com.euvic.euvic_staz_marvel.models.CharactersDataClass
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import io.reactivex.subjects.Subject

interface MainView : MvpView {
    val getCharacters: Observable<Int>
    val searchCharacters: Observable<CharSequence>
    val getDetails: Observable<Int>
    fun render(viewState: MainViewState)
}