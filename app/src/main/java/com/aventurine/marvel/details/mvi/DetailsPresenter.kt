package com.aventurine.marvel.details.mvi

import com.aventurine.marvel.mosby.MviBasePresenter
import com.aventurine.marvel.apiservice.MarvelDatasource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailsPresenter() : MviBasePresenter<DetailsView, DetailsViewState>() {

    private val reducer by lazy { DetailsReducer() }
    private val marvelDatasource: MarvelDatasource = MarvelDatasource()

    override fun bindIntents() {
        val getDetails: Observable<PartialDetailsState> = intent(object : ViewIntentBinder<DetailsView, Int> {
            override fun bind(view: DetailsView): Observable<Int> {
                return view.getDetails
            }
        })
            .observeOn(Schedulers.io())
            .flatMap { characterId ->
                marvelDatasource.getDetails(characterId)
            }
            .map { characterDetails ->
                PartialDetailsState.ReceivedDetails(characterDetails) as PartialDetailsState
            }
            .startWithItem(PartialDetailsState.Loading(true))
            .onErrorReturn { error -> PartialDetailsState.Error(error) }

        val getSeries: Observable<PartialDetailsState> = intent(object : ViewIntentBinder<DetailsView, Int> {
            override fun bind(view: DetailsView): Observable<Int> {
                return view.getSeries
            }
        })
            .observeOn(Schedulers.io())
            .flatMap { characterId ->
                marvelDatasource.getSeriesByCharacterId(characterId)
            }
            .map { seriesDetails ->
                PartialDetailsState.ReceivedSeries(seriesDetails) as PartialDetailsState
            }
            .startWithItem(PartialDetailsState.Loading(true))
            .onErrorReturn { error -> PartialDetailsState.Error(error) }

        val stream = Observable
            .merge(getDetails, getSeries)
            .observeOn(AndroidSchedulers.mainThread())
            .scan(DetailsViewState()) { previousState: DetailsViewState, changedState: PartialDetailsState ->
                return@scan reducer.reduce(previousState, changedState)
            }

        subscribeViewState(stream,
            object : ViewStateConsumer<DetailsView, DetailsViewState> {
                override fun accept(view: DetailsView, viewState: DetailsViewState) {
                    view.render(viewState)
                }
            })

    }

}