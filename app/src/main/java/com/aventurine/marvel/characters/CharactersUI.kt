package com.aventurine.marvel.characters

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.aventurine.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class CharactersUI : AnkoComponent<ViewGroup> {
    val cardView: CardView? = null
    val scale: Float = 0.2f
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        cardView {
            cardElevation = 10f
            lparams(matchParent, wrapContent) {
                bottomMargin = dip(16)
                leftMargin = dip(8)
                rightMargin = dip(8)
            }
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 16f
                setColor(Color.BLACK)
                linearLayout {
                    lparams(matchParent, wrapContent) {
                        imageView() {
                            id = R.id.heroImage
                        }
                        textView() {
                            id = R.id.heroName
                            textColor = Color.WHITE
                            leftPadding = dip(16)
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(wrapContent, matchParent)
                    }
                }
            }
        }
    }
}