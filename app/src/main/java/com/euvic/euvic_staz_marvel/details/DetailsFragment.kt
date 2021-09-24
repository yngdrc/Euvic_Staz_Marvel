package com.euvic.euvic_staz_marvel.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.euvic.euvic_staz_marvel.R
import com.euvic.euvic_staz_marvel.details.mvi.DetailsPresenter
import com.euvic.euvic_staz_marvel.details.mvi.DetailsView
import com.euvic.euvic_staz_marvel.details.mvi.DetailsViewState
import com.euvic.euvic_staz_marvel.models.characters.ItemSeries
import com.euvic.euvic_staz_marvel.models.characters.CharactersResult
import com.euvic.euvic_staz_marvel.models.series.SeriesResult
import com.hannesdorfmann.mosby3.mvi.MviFragment
import io.reactivex.Observable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.dip

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DetailsFragment : MviFragment<DetailsView, DetailsPresenter>(), DetailsView {
    private var characterID: Int? = null
    private lateinit var detailsFragmentUI: DetailsFragmentUI
    private var seriesList: MutableList<SeriesResult> = mutableListOf()
    private val seriesAdapter: SeriesAdapter

    init {
        seriesAdapter = SeriesAdapter(seriesList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            characterID = it.getInt("characterID")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailsFragmentUI = DetailsFragmentUI(seriesAdapter)
        return detailsFragmentUI.createView(AnkoContext.create(ctx, this))
    }

    companion object {
        fun newInstance(param1: Int) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt("characterID", param1)
                }
            }
    }

    override fun createPresenter(): DetailsPresenter = DetailsPresenter()

    override val getDetails: Observable<Int>
        get() = Observable.just(characterID)

    override val getSeries: Observable<Int>
        get() = Observable.just(characterID)

    override fun render(viewState: DetailsViewState) {
        if(viewState.loading) {

        }
        if(viewState.details!=null) {
            Log.d("DetailsFragment", viewState.details.toString())
            detailsFragmentUI.swipeRefreshLayout.isRefreshing = viewState.loading
            val details = viewState.details!!.data.results[0]
            setDetailsContent(details)
        }
        if(viewState.series!=null) {
            Log.d("SERIES", viewState.series.toString())
            detailsFragmentUI.swipeRefreshLayout.isRefreshing = viewState.loading
            val series = viewState.series!!.data.results
            setSeriesContent(series)
        }
        if(viewState.error!=null) {
            Log.d("DetailsFragment", viewState.error.toString())
            detailsFragmentUI.swipeRefreshLayout.isRefreshing = viewState.loading
        }
    }

    private fun setDetailsContent(details: CharactersResult) {
        detailsFragmentUI.characterImage.layoutParams.height = dip(100)
        detailsFragmentUI.characterImage.layoutParams.width = dip(100)
        Glide.with(requireContext())
            .load("${details.thumbnail?.path?.replace("http", "https")}/standard_fantastic.${details.thumbnail?.extension}")
            .placeholder(R.drawable.image_placeholder_marvel_circle)
            .circleCrop()
            .into(detailsFragmentUI.characterImage)
        detailsFragmentUI.characterName.text = details.name
        if (details.description.equals("")) {
            detailsFragmentUI.characterDescription.text = "No description to show"
        } else {
            detailsFragmentUI.characterDescription.text = details.description
        }
    }

    private fun setSeriesContent(series: MutableList<SeriesResult>) {
        detailsFragmentUI.seriesProgressBar.isVisible = false
        addToAdapter(series, seriesList)
    }

    private fun clearAdapter(list: MutableList<SeriesResult>) {
        val size: Int = list.size
        list.clear()
        seriesAdapter.notifyItemRangeRemoved(0, size)
    }

    private fun addToAdapter(newResult: MutableList<SeriesResult>, list: MutableList<SeriesResult>) {
        list.addAll(newResult)
        seriesAdapter.notifyItemInserted(list.size - 1)
    }
}