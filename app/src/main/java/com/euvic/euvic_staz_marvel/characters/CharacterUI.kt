package com.euvic.euvic_staz_marvel.characters

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class CharacterUI : AnkoComponent<ViewGroup> {
    val cardView: CardView? = null
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        cardView {
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 32f
                setColor(Color.BLACK)
                relativeLayout {
                    lparams(matchParent, wrapContent) {
                        horizontalPadding = dip(16)
                        verticalPadding = dip(8)
                    }
                }
            }
        }
    }
}