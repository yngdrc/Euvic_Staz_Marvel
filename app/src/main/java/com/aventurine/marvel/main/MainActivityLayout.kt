package com.aventurine.marvel.main

import android.content.Context
import android.widget.LinearLayout
import androidx.fragment.app.FragmentContainerView
import com.aventurine.marvel.utils.fragmentContainerView
import org.jetbrains.anko.matchParent

class MainActivityLayout(context: Context) : LinearLayout(context) {
    val fragmentContainer: FragmentContainerView

    init {
        id = generateViewId()
        layoutParams = LayoutParams(matchParent, matchParent)

        fragmentContainer = fragmentContainerView {
            id = generateViewId()
            layoutParams = LayoutParams(matchParent, matchParent)
        }
    }
}