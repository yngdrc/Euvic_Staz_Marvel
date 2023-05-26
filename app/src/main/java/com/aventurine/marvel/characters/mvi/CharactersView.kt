package com.aventurine.marvel.characters.mvi

import com.aventurine.marvel.characters.CharacterItemAction
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.subjects.PublishSubject

interface CharactersView : MvpView {
    val refreshIntent: Observable<Unit>
    val searchIntent: Observable<CharSequence>
    val scrollEvents: Observable<Int>
    val itemActionIntent: Observable<CharacterItemAction>
    fun render(viewState: CharactersViewState)
}