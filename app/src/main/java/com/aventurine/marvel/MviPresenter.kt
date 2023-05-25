package com.aventurine.marvel

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface MviPresenter<V : MvpView?, VS> : MvpPresenter<V>