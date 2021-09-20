package com.euvic.euvic_staz_marvel.characters

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.euvic.euvic_staz_marvel.apiservice.MarvelDatasource
import com.euvic.euvic_staz_marvel.main.MainActivity
import com.euvic.euvic_staz_marvel.main.MainView
import com.euvic.euvic_staz_marvel.main.MainViewState
import com.euvic.euvic_staz_marvel.models.CharactersDataClass
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
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