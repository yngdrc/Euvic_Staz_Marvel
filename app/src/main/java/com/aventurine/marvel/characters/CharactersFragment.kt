package com.aventurine.marvel.characters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aventurine.R
import com.aventurine.marvel.mosby.MviFragment
import com.aventurine.marvel.characters.mvi.CharactersFragmentPresenter
import com.aventurine.marvel.characters.mvi.CharactersView
import com.aventurine.marvel.characters.mvi.CharactersViewState
import com.aventurine.marvel.db.models.characters.CharactersResult
import com.jakewharton.rxbinding4.recyclerview.scrollEvents
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding4.widget.queryTextChanges
import io.reactivex.rxjava3.core.Observable
import io.reactivex.subjects.PublishSubject
import java.util.Optional

class CharactersFragment : MviFragment<CharactersView, CharactersFragmentPresenter>(),
    CharactersView {
    private var layout: CharactersFragmentLayout? = null

    override val refreshIntent: Observable<Unit>
        get() = layout?.swipeRefreshLayout?.refreshes() ?: Observable.empty()

    override val searchIntent: Observable<CharSequence>
        get() = layout?.searchView?.queryTextChanges()?.skipInitialValue() ?: Observable.empty()

    override val itemActionIntent: Observable<CharacterItemAction>
        get() = layout?.charactersAdapter?.actionRelay ?: Observable.empty()

    override val scrollEvents: Observable<Int>
        get() = layout?.charactersRecyclerView?.scrollEvents()?.map { event ->
            Optional.ofNullable(layout) to event
        }?.filter { it.first.isPresent }?.map {
            it.first.get() to it.second
        }?.filter { (layout, event) ->
            val layoutManager = event.view.layoutManager as LinearLayoutManager
            layoutManager.findLastCompletelyVisibleItemPosition() == layout.charactersAdapter.itemCount - 1
                    && layout.searchView.query.isEmpty()
                    && layout.charactersAdapter.itemCount != 0
        }?.map { (layout, _) ->
            layout.charactersAdapter.itemCount
        } ?: Observable.empty()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = CharactersFragmentLayout(requireContext()).also { layout = it }

    override fun createPresenter(): CharactersFragmentPresenter = CharactersFragmentPresenter()

    override fun render(viewState: CharactersViewState) {
        if (viewState.navigate != null) {
            val bundle = Bundle().apply {
                putInt("characterID", viewState.navigate!!)
            }
            return findNavController().navigate(
                R.id.action_charactersFragment_to_detailsFragment,
                bundle
            )
        }

        if (viewState.characters != null)
            renderCharacters(state = viewState)
    }

    private fun renderCharacters(state: CharactersViewState) = state.characters?.let {
        layout?.swipeRefreshLayout?.isRefreshing = state.loading
        layout?.charactersAdapter?.submitList(list = it.data.results)
    }
}