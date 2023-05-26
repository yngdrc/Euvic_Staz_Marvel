package com.aventurine.marvel.characters

import android.content.Context
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

class CharactersFragmentLayout(
    context: Context
) : LinearLayout(context) {
    val searchView: SearchView
    val swipeRefreshLayout: SwipeRefreshLayout
    lateinit var charactersRecyclerView: RecyclerView

    val charactersAdapter: CharactersAdapter = CharactersAdapter()

    init {
        orientation = VERTICAL

        searchView = searchView {
            queryHint = "Search..."
            isIconifiedByDefault = false
        }

        swipeRefreshLayout = swipeRefreshLayout {
            linearLayout {
                charactersRecyclerView = recyclerView {
                    backgroundColor = Color.WHITE
                    layoutManager = LinearLayoutManager(context)
                    adapter = charactersAdapter
                }.lparams(matchParent, wrapContent)
            }
        }
    }
}