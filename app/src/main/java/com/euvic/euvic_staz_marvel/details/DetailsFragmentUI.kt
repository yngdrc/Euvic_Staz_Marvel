package com.euvic.euvic_staz_marvel.details

import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.euvic.euvic_staz_marvel.characters.CharactersFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class DetailsFragmentUI: AnkoComponent<CharactersFragment> {
    private lateinit var rv: RecyclerView
    override fun createView(ui: AnkoContext<CharactersFragment>) = ui.apply {
        relativeLayout {
            backgroundColor = Color.BLUE
            lparams(matchParent, matchParent)

            rv = recyclerView {
                backgroundColor = Color.WHITE
                layoutManager = LinearLayoutManager(ctx)
            }.lparams(matchParent, matchParent) {

            }
        }
    }.view
}