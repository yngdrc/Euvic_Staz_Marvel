package com.aventurine.marvel.utils

import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import org.jetbrains.anko.custom.ankoView

fun ViewGroup.fragmentContainerView(
    init: FragmentContainerView.() -> Unit = {}
) = ankoView(
    factory = { FragmentContainerView(it) },
    theme = 0,
    init = init
)