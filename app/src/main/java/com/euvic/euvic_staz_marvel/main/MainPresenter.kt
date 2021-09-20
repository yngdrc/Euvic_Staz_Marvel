package com.euvic.euvic_staz_marvel.main

import android.util.Log
import com.euvic.euvic_staz_marvel.apiservice.MarvelDatasource
import com.euvic.euvic_staz_marvel.characters.CharactersAdapter
import com.euvic.euvic_staz_marvel.main.PartialMainState.Loading
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter() : MviBasePresenter<MainView, MainViewState>() {

    private val reducer by lazy { MainReducer() }
    private val marvelDatasource: MarvelDatasource = MarvelDatasource()
    private lateinit var adapter: CharactersAdapter

    // tu utworzyc datasource
    override fun bindIntents() {
        val getCharacters: Observable<PartialMainState> = intent { it.getCharacters }
            .flatMap { offset ->
                marvelDatasource.getCharacters(offset).map {
                    PartialMainState.GotCharacters(it) as PartialMainState
                }.subscribeOn(Schedulers.io())
            }
            .startWith(Loading(true))
            .onErrorReturn { error -> PartialMainState.Error(error) }
            .subscribeOn(Schedulers.io())

        val searchCharacters: Observable<PartialMainState> = intent { it.searchCharacters }
            .flatMap { searchText ->
                Log.d("search", searchText.toString())
                marvelDatasource.searchCharacters(searchText).map {
                    PartialMainState.FoundCharacters(it) as PartialMainState
                }.subscribeOn(Schedulers.io())
            }
            .startWith(Loading(true))
            .onErrorReturn { error -> PartialMainState.Error(error) }
            .subscribeOn(Schedulers.io())

        val stream = Observable
            .merge(getCharacters, searchCharacters)
            .observeOn(AndroidSchedulers.mainThread())
            .scan(MainViewState()) { previousState: MainViewState, changedState: PartialMainState ->
                return@scan reducer.reduce(previousState, changedState)
            }

        subscribeViewState(stream) { view, viewState -> view.render(viewState) }

    }

}