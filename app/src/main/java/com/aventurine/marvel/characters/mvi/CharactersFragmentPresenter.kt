package com.aventurine.marvel.characters.mvi

import com.aventurine.marvel.mosby.MviBasePresenter
import com.aventurine.marvel.apiservice.MarvelDatasource
import com.aventurine.marvel.characters.CharacterItemAction
import com.aventurine.marvel.characters.mvi.CharactersViewStateChange.Loading
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class CharactersFragmentPresenter : MviBasePresenter<CharactersView, CharactersViewState>() {

    private val reducer by lazy { CharactersViewStateReducer() }
    private val marvelDatasource: MarvelDatasource = MarvelDatasource()

    override fun bindIntents() {
        val refreshIntent = Observable.fromCallable { Unit }
            .mergeWith(intent { it.refreshIntent })
            .switchMap { marvelDatasource.getCharacters(offset = 0) }
            .map { response -> CharactersViewStateChange.CharactersFetched(characters = response) }

        val scrollEvents = intent { it.scrollEvents }
            .switchMap { offset -> marvelDatasource.getCharacters(offset = offset) }
            .map { response -> CharactersViewStateChange.CharactersFetched(characters = response) }

        val searchIntent = intent { it.searchIntent }
            .switchMap { query -> marvelDatasource.searchCharacters(searchText = query) }
            .map { response -> CharactersViewStateChange.CharactersFetched(characters = response) }

        val itemActionIntent = intent { it.itemActionIntent }
            .switchMap { handleItemAction(it) }

        val stream = Observable
            .merge(refreshIntent, scrollEvents, searchIntent, itemActionIntent)
            .observeOn(AndroidSchedulers.mainThread())
            .scan(CharactersViewState()) { previousState: CharactersViewState, changedState: CharactersViewStateChange ->
                return@scan reducer.reduce(previousState, changedState)
            }

        subscribeViewState(stream,
            object : ViewStateConsumer<CharactersView, CharactersViewState> {
                override fun accept(view: CharactersView, viewState: CharactersViewState) {
                    view.render(viewState)
                }
            })
    }

    fun handleItemAction(
        action: CharacterItemAction
    ): Observable<CharactersViewStateChange> = when (action) {
        is CharacterItemAction.Navigate -> {
            CharactersViewStateChange.Navigate(characterId = action.characterId)
        }
    }.let { Observable.just(it) }

}