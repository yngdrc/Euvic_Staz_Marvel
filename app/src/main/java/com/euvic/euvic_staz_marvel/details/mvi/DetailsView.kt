package com.euvic.euvic_staz_marvel.details.mvi

import com.euvic.euvic_staz_marvel.characters.mvi.MainViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

interface DetailsView : MvpView {
    val getDetails: Observable<Int>
    val getSeries: Observable<Int>
    fun render(viewState: DetailsViewState)
}