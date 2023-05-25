package com.aventurine.marvel.characters.mvi

import com.aventurine.marvel.mosby.MviBasePresenter
import com.aventurine.marvel.apiservice.MarvelDatasource
import com.aventurine.marvel.characters.mvi.PartialMainState.Loading
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import com.aventurine.marvel.mosby.ViewIntentBinder

class MainPresenter : MviBasePresenter<MainView, MainViewState>() {

    private val reducer by lazy { MainReducer() }
    private val marvelDatasource: MarvelDatasource = MarvelDatasource()

    override fun bindIntents() {
        val getCharacters: Observable<PartialMainState> = intent { it.getCharacters }
                .observeOn(Schedulers.io())
                .flatMap { offset ->
                    marvelDatasource.getCharacters(offset)
                }.map { characters ->
                    PartialMainState.GotCharacters(characters) as PartialMainState
                }
                .startWithItem(Loading(true))
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
                    PartialMainState.FoundCharacters(characters) as PartialMainState
                }
                .startWithItem(Loading(true))
                .onErrorReturn { error -> PartialMainState.Error(error) }

        val stream = Observable
            .merge(getCharacters, searchCharacters)
            .observeOn(AndroidSchedulers.mainThread())
            .scan(MainViewState()) { previousState: MainViewState, changedState: PartialMainState ->
                return@scan reducer.reduce(previousState, changedState)
            }

        subscribeViewState(stream,
            object : ViewStateConsumer<MainView, MainViewState> {
                override fun accept(view: MainView, viewState: MainViewState) {
                    view.render(viewState)
                }
            })
    }

}