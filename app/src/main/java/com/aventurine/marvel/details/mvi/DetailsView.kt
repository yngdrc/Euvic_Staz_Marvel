package com.aventurine.marvel.details.mvi

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.rxjava3.core.Observable

interface DetailsView : MvpView {
    val getDetails: Observable<Int>
    val getSeries: Observable<Int>
    fun render(viewState: DetailsViewState)
}