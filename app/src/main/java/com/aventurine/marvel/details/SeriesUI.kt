package com.aventurine.marvel.details

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.aventurine.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class SeriesUI : AnkoComponent<ViewGroup> {
    val scale: Float = 0.2f
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        cardView {
            cardElevation = 10f
            lparams(matchParent, matchParent) {
                margin = dip(16)
            }
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 16f
                setColor(Color.BLACK)
                linearLayout {
                    lparams(matchParent, matchParent) {
                        imageView() {
                            id = R.id.seriesImage
                        }.lparams(wrapContent, matchParent)
                        textView() {
                            id = R.id.seriesName
                            textColor = Color.WHITE
                            gravity = Gravity.CENTER
                            padding = dip(16)
                        }.lparams(matchParent, matchParent)
                    }
                }
            }
        }
    }
}