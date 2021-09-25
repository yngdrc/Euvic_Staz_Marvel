package com.euvic.euvic_staz_marvel.details.mvi

import com.euvic.euvic_staz_marvel.apiservice.MarvelDatasource
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsPresenter() : MviBasePresenter<DetailsView, DetailsViewState>() {

    private val reducer by lazy { DetailsReducer() }
    private val marvelDatasource: MarvelDatasource = MarvelDatasource()

    override fun bindIntents() {
        val getDetails: Observable<PartialDetailsState> = intent { it.getDetails }
            .observeOn(Schedulers.io())
            .flatMap { characterId ->
                marvelDatasource.getDetails(characterId)
            }
            .map { characterDetails ->
                PartialDetailsState.ReceivedDetails(characterDetails) as PartialDetailsState
            }
            .startWith(PartialDetailsState.Loading(true))
            .onErrorReturn { error -> PartialDetailsState.Error(error) }

        val getSeries: Observable<PartialDetailsState> = intent { it.getSeries }
            .observeOn(Schedulers.io())
            .flatMap { characterId ->
                marvelDatasource.getSeriesByCharacterId(characterId)
            }
            .map { seriesDetails ->
                PartialDetailsState.ReceivedSeries(seriesDetails) as PartialDetailsState
            }
            .startWith(PartialDetailsState.Loading(true))
            .onErrorReturn { error -> PartialDetailsState.Error(error) }

        val stream = Observable
            .merge(getDetails, getSeries)
            .observeOn(AndroidSchedulers.mainThread())
            .scan(DetailsViewState()) { previousState: DetailsViewState, changedState: PartialDetailsState ->
                return@scan reducer.reduce(previousState, changedState)
            }

        subscribeViewState(stream) { view, viewState -> view.render(viewState) }

    }

}