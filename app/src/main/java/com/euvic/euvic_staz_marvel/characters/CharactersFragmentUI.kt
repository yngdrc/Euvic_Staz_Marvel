package com.euvic.euvic_staz_marvel.characters

import android.graphics.Color
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class CharactersFragmentUI(private val adapter: CharactersAdapter): AnkoComponent<CharactersFragment> {
    lateinit var rv: RecyclerView
    lateinit var textView: TextView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var searchView: SearchView
    var charactersAdapter: CharactersAdapter = adapter
    override fun createView(ui: AnkoContext<CharactersFragment>) = ui.apply {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            searchView = searchView {
                queryHint = "Search..."
            }
            swipeRefreshLayout = swipeRefreshLayout {
                linearLayout {
                    rv = recyclerView {
                        backgroundColor = Color.WHITE
                        layoutManager = LinearLayoutManager(ctx)
                        adapter = charactersAdapter
                    }.lparams(matchParent, wrapContent)
                    textView = textView {
                        text = "asd"
                    }
                }
            }
        }
    }.view
}