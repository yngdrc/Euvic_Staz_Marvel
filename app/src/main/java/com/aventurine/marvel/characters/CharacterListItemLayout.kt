package com.aventurine.marvel.characters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.aventurine.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class CharacterListItemLayout(context: Context) : CardView(context) {
    lateinit var heroImage: ImageView
    lateinit var heroName: TextView

    init {
        cardElevation = 10f
        layoutParams = LayoutParams(matchParent, wrapContent).apply {
            bottomMargin = dip(16)
            leftMargin = dip(8)
            rightMargin = dip(8)
        }
        radius = 16f

        linearLayout {
            layoutParams = LayoutParams(matchParent, wrapContent)
            backgroundColorResource = android.R.color.background_dark

            heroImage = imageView().lparams(dip(50), dip(50))

            heroName = textView {
                textColorResource = android.R.color.white
                leftPadding = dip(16)
            }.lparams(wrapContent, wrapContent) {
                gravity = Gravity.CENTER_VERTICAL
            }
        }
    }
}