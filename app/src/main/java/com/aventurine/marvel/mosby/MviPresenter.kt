package com.aventurine.marvel.mosby

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface MviPresenter<V : MvpView?, VS> : MvpPresenter<V>