package com.euvic.euvic_staz_marvel.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.euvic.euvic_staz_marvel.R
import com.euvic.euvic_staz_marvel.details.mvi.DetailsPresenter
import com.euvic.euvic_staz_marvel.details.mvi.DetailsView
import com.euvic.euvic_staz_marvel.details.mvi.DetailsViewState
import com.euvic.euvic_staz_marvel.models.Result
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.dip

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DetailsFragment : MviFragment<DetailsView, DetailsPresenter>(), DetailsView {
    private var characterID: Int? = null
    private lateinit var detailsFragmentUI: DetailsFragmentUI
    private val subject: PublishSubject<Int> = PublishSubject.create()

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
        detailsFragmentUI = DetailsFragmentUI()
        return detailsFragmentUI.createView(AnkoContext.create(ctx, this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override val emptyIntent: Observable<Int>
        get() = detailsFragmentUI.swipeRefreshLayout.refreshes()
            .map {
                characterID
            }

    override fun render(viewState: DetailsViewState) {
        if(viewState.loading) {

        }
        if(viewState.details!=null) {
            Log.d("DetailsFragment", viewState.details.toString())
            detailsFragmentUI.swipeRefreshLayout.isRefreshing = viewState.loading
            val details = viewState.details!!.data.results[0]
            setDetailsContent(details)
        }
        if(viewState.empty==1) {
            Log.d("Empty", "empty")
            detailsFragmentUI.swipeRefreshLayout.isRefreshing = viewState.loading
        }
        if(viewState.error!=null) {
            Log.d("DetailsFragment", viewState.error.toString())
            detailsFragmentUI.swipeRefreshLayout.isRefreshing = viewState.loading
        }
    }

    private fun setDetailsContent(details: Result) {
        detailsFragmentUI.characterImage.layoutParams.height = dip(100)
        detailsFragmentUI.characterImage.layoutParams.width = dip(100)
        Glide.with(requireContext())
            .load("${details.thumbnail?.path?.replace("http", "https")}/standard_fantastic.${details.thumbnail?.extension}")
            .placeholder(R.drawable.image_placeholder_marvel_circle)
            .circleCrop()
            .into(detailsFragmentUI.characterImage)
        detailsFragmentUI.characterName.text = details.name
        detailsFragmentUI.characterDescription.text = details.description
    }
}