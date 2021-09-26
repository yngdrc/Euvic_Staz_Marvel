package com.euvic.euvic_staz_marvel.characters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.euvic.euvic_staz_marvel.characters.mvi.MainPresenter
import com.euvic.euvic_staz_marvel.characters.mvi.MainView
import com.euvic.euvic_staz_marvel.characters.mvi.MainViewState
import com.euvic.euvic_staz_marvel.db.models.characters.CharactersResult
import com.euvic.euvic_staz_marvel.utils.Constants.Companion.DEFAULT_OFFSET
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding3.recyclerview.scrollEvents
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class CharactersFragment() : MviFragment<MainView, MainPresenter>(), MainView {
    private var param1: String? = null
    private val adapter: CharactersAdapter
    private lateinit var charactersFragmentUI: CharactersFragmentUI
    private var charactersList: MutableList<CharactersResult> = mutableListOf()
    private lateinit var searchViewEditText: EditText

    init {
        adapter = CharactersAdapter(charactersList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        charactersFragmentUI = CharactersFragmentUI(adapter)
        return charactersFragmentUI.createView(AnkoContext.create(ctx, this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewEditText = charactersFragmentUI.searchView.findViewById<EditText>(charactersFragmentUI.searchView.context.resources.getIdentifier("android:id/search_src_text", null, null))
    }

    companion object {
        fun newInstance(param1: String) =
            CharactersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun createPresenter(): MainPresenter = MainPresenter()

    // rozdzielic intenty
    // zeby usunac
    override val getCharacters: Observable<Int>
        get() = Observable.merge(
            charactersFragmentUI.swipeRefreshLayout.refreshes()
                .mergeWith(
                    searchViewEditText.textChanges()
                        .filter { searchText ->
                            searchText.isEmpty()
                        }
                        .map {
                            clearAdapter(charactersList)
                            Unit
                        }
                )
                .map {
                    clearAdapter(charactersList)
                    DEFAULT_OFFSET
                },
            charactersFragmentUI.rv.scrollEvents()
                .distinctUntilChanged()
                .filter {
                    val layoutManager = it.view.layoutManager as LinearLayoutManager
                    layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1
                        && charactersFragmentUI.searchView.query.isEmpty()
                            && adapter.itemCount != 0
                }
                .map {
                    adapter.itemCount
                }
        )

    override val searchCharacters: Observable<CharSequence>
        get() = searchViewEditText.textChanges()
            .skipInitialValue()
            .map { searchText ->
                searchText.trim()
            }
            .filter { searchText ->
                searchText.isNotEmpty()
            }

    override fun onPause() {
        super.onPause()
        clearAdapter(charactersList)
    }

    override fun render(viewState: MainViewState) {
        if(viewState.loading) {
        }
        if(viewState.characters!=null) {
            charactersFragmentUI.swipeRefreshLayout.isRefreshing = viewState.loading
            //addToAdapter(viewState.characters!!.data.results, charactersList)
            addToAdapter(viewState.characters!!.data.results, charactersList)
        }
        if(viewState.foundCharacters!=null) {
            charactersFragmentUI.swipeRefreshLayout.isRefreshing = viewState.loading
            clearAdapter(charactersList)
            addToAdapter(viewState.foundCharacters!!.data.results, charactersList)
        }
        if(viewState.error!=null) {
            Log.d("ViewState", viewState.error.toString())
            charactersFragmentUI.swipeRefreshLayout.isRefreshing = viewState.loading
        }
    }

    private fun clearAdapter(list: MutableList<CharactersResult>) {
        val size: Int = list.size
        list.clear()
        adapter.notifyItemRangeRemoved(0, size)
    }

    private fun addToAdapter(newResult: MutableList<CharactersResult>, list: MutableList<CharactersResult>) {
        list.addAll(newResult)
        adapter.notifyItemInserted(list.size - 1)
    }
}