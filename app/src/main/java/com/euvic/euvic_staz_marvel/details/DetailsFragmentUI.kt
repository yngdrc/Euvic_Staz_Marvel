package com.euvic.euvic_staz_marvel.details

import android.graphics.Color
import android.text.style.AlignmentSpan
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.euvic.euvic_staz_marvel.characters.CharactersFragment
import com.euvic.euvic_staz_marvel.models.CharactersDataClass
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class DetailsFragmentUI(): AnkoComponent<DetailsFragment> {
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var characterName: TextView
    lateinit var characterImage: ImageView
    lateinit var characterDescription: TextView
    override fun createView(ui: AnkoContext<DetailsFragment>) = ui.apply {
        swipeRefreshLayout = swipeRefreshLayout() {
            linearLayout() {
                lparams(matchParent, matchParent)
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                characterImage = imageView() {
                }
                characterName = textView() {
                    textColor = Color.BLACK
                    topPadding = dip(8)
                }.lparams(wrapContent, wrapContent)
                characterDescription = textView() {
                    textColor = Color.BLACK
                    padding = dip(16)
                }.lparams(wrapContent, wrapContent)
            }
        }
    }.view
}