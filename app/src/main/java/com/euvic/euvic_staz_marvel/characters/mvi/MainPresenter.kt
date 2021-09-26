package com.euvic.euvic_staz_marvel.characters.mvi

import android.content.Context
import android.util.Log
import com.euvic.euvic_staz_marvel.apiservice.MarvelDatasource
import com.euvic.euvic_staz_marvel.characters.mvi.PartialMainState.Loading
import com.euvic.euvic_staz_marvel.db.CharactersRepo
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter() : MviBasePresenter<MainView, MainViewState>() {

    private val reducer by lazy { MainReducer() }
    private val marvelDatasource: MarvelDatasource = MarvelDatasource()

    // tu utworzyc datasource
    override fun bindIntents() {
        val getCharacters: Observable<PartialMainState> = intent { it.getCharacters }
            .observeOn(Schedulers.io())
            .flatMap { offset ->
                marvelDatasource.getCharacters(offset)
            }.map { characters ->
                PartialMainState.GotCharacters(characters.data.results) as PartialMainState
            }
            .startWith(Loading(true))
            .onErrorReturn { error ->
                PartialMainState.Error(error)
            }

        // zmienic na getCharacters jak bedzie puste
        val searchCharacters: Observable<PartialMainState> = intent { it.searchCharacters }
            .observeOn(Schedulers.io())
            .flatMap { searchText ->
                marvelDatasource.searchCharacters(searchText)
            }
            .map { characters ->
                PartialMainState.FoundCharacters(characters.data.results) as PartialMainState
            }
            .startWith(Loading(true))
            .onErrorReturn { error -> PartialMainState.Error(error) }

        val stream = Observable
            .merge(getCharacters, searchCharacters)
            .observeOn(AndroidSchedulers.mainThread())
            .scan(MainViewState()) { previousState: MainViewState, changedState: PartialMainState ->
                return@scan reducer.reduce(previousState, changedState)
            }

        subscribeViewState(stream) { view, viewState -> view.render(viewState) }

    }

}