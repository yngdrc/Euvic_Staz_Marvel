package com.euvic.euvic_staz_marvel.characters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.euvic.euvic_staz_marvel.R
import com.euvic.euvic_staz_marvel.apiservice.MarvelDatasource
import com.euvic.euvic_staz_marvel.main.MainPresenter
import com.euvic.euvic_staz_marvel.main.MainView
import com.euvic.euvic_staz_marvel.main.MainViewState
import com.euvic.euvic_staz_marvel.main.PartialMainState
import com.euvic.euvic_staz_marvel.models.CharactersDataClass
import com.euvic.euvic_staz_marvel.models.Result
import com.euvic.euvic_staz_marvel.utils.Constants.Companion.DEFAULT_OFFSET
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding3.recyclerview.scrollEvents
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.queryTextChanges
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.Android
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.support.v4.ctx
import java.util.concurrent.TimeUnit

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class CharactersFragment() : MviFragment<MainView, MainPresenter>(), MainView {
    private var param1: String? = null
    private val adapter: CharactersAdapter
    private lateinit var charactersFragmentUI: CharactersFragmentUI
    private var charactersList: MutableList<Result> = mutableListOf()

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

    companion object {
        fun newInstance(param1: String) =
            CharactersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun createPresenter(): MainPresenter = MainPresenter()

    override val getCharacters: Observable<Int>
        get() = Observable.merge(
            charactersFragmentUI.swipeRefreshLayout.refreshes()
                .map {
                    DEFAULT_OFFSET
                },
            charactersFragmentUI.rv.scrollEvents()
                .distinctUntilChanged()
                .filter {
                    val layoutManager = it.view.layoutManager as LinearLayoutManager
                    layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1
                }
                .map {
                    adapter.itemCount
                }
        )

    override val searchCharacters: Observable<CharSequence>
        get() = charactersFragmentUI.searchView.queryTextChanges()
            .filter { searchText ->
                searchText.isNotEmpty()
            }
            .debounce(500, TimeUnit.MILLISECONDS)
            .map { searchText ->
                searchText.trim()
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())

    override fun render(viewState: MainViewState) {
        if(viewState.loading) {
            Log.d("ViewState", "loading")
        }
        if(viewState.characters!=null) {
            Log.d("ViewState", viewState.characters.toString())
            charactersFragmentUI.swipeRefreshLayout.isRefreshing = viewState.loading
            charactersList.addAll(viewState.characters!!.data.results)
            adapter.notifyItemInserted(adapter.characters.size - 1)
        }
        if(viewState.foundCharacters!=null) {
            Log.d("ViewState", viewState.foundCharacters.toString())
            charactersFragmentUI.swipeRefreshLayout.isRefreshing = viewState.loading
            val count = charactersList.size
            charactersList.clear()
            adapter.notifyItemRangeRemoved(0, count)
            charactersList.addAll(viewState.foundCharacters!!.data.results)
            adapter.notifyItemInserted(charactersList.size - 1)
        }
        if(viewState.error!=null) {
            Log.d("ViewState", viewState.error.toString())
            charactersFragmentUI.swipeRefreshLayout.isRefreshing = viewState.loading
        }
    }
}