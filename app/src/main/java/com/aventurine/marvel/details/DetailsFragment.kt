package com.aventurine.marvel.details

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.aventurine.marvel.details.mvi.DetailsPresenter
import com.aventurine.marvel.details.mvi.DetailsView
import com.aventurine.marvel.details.mvi.DetailsViewState
import com.aventurine.marvel.db.models.characters.CharactersResult
import com.aventurine.marvel.db.models.series.SeriesResult
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.dip
import androidx.recyclerview.widget.RecyclerView
import com.aventurine.R
import com.aventurine.marvel.MviFragment
import com.bumptech.glide.request.RequestOptions
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import io.reactivex.rxjava3.core.Observable
import jp.wasabeef.glide.transformations.BlurTransformation
import org.jetbrains.anko.windowManager
import kotlin.properties.Delegates


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DetailsFragment : MviFragment<DetailsView, DetailsPresenter>(), DetailsView {
    private var characterID by Delegates.notNull<Int>()
    private lateinit var detailsFragmentUI: DetailsFragmentUI
    private var seriesList: MutableList<SeriesResult> = mutableListOf()
    private val seriesAdapter: SeriesAdapter
    private var shortAnimationDuration: Int = 0
    var scrollDx = 0

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
            .mergeWith(detailsFragmentUI.swipeRefreshLayout.refreshes()
                .map {
                    characterID
                })

    override val getSeries: Observable<Int>
        get() = Observable.just(characterID)
            .mergeWith(detailsFragmentUI.swipeRefreshLayout.refreshes()
                .map {
                    characterID
                })

    override fun render(viewState: DetailsViewState) {
        if(viewState.loading) {

        }
        if(viewState.details!=null) {
            detailsFragmentUI.swipeRefreshLayout.isRefreshing = viewState.loading
            val details = viewState.details!!.data.results[0]
            setDetailsContent(details)
        }
        if(viewState.series!=null) {
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
            .load("${details.thumbnail.path.replace("http", "https")}/standard_fantastic.${details.thumbnail.extension}")
            .placeholder(R.drawable.image_placeholder_marvel_circle)
            .circleCrop()
            .into(detailsFragmentUI.characterImage)

        val displayMetrics = DisplayMetrics()
        ctx.windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels
        Log.d("METRICS", "$width, $height")

        Glide.with(requireContext())
            .load("${details.thumbnail.path.replace("http", "https")}/detail.${details.thumbnail.extension}")
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25,3)).override(width, height))
            .into(detailsFragmentUI.backgroundImage)

        detailsFragmentUI.characterName.text = details.name
        if (details.description == "") {
            detailsFragmentUI.characterDescription.text = "No description to show"
        } else {
            detailsFragmentUI.characterDescription.text = details.description
        }
    }

    private fun setSeriesContent(series: MutableList<SeriesResult>) {
        detailsFragmentUI.seriesProgressBar.isVisible = false
        addToAdapter(series, seriesList)
        detailsFragmentUI.seriesInfo.text = series[scrollDx].description
        detailsFragmentUI.rv.setOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val llm = detailsFragmentUI.rv.layoutManager as LinearLayoutManager
                    when(newState) {
                        0 -> {
                            scrollDx = llm.findLastVisibleItemPosition()
                            detailsFragmentUI.seriesInfo.text = series[scrollDx].description
                            detailsFragmentUI.seriesInfo.apply {
                                shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
                                alpha = 0f
                                visibility = View.VISIBLE
                                animate()
                                    .alpha(1f)
                                    .setDuration(shortAnimationDuration.toLong())
                                    .setListener(null)
                            }
                        }
                        2 -> {
                            detailsFragmentUI.seriesInfo.apply {
                                shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
                                alpha = 1f
                                animate()
                                    .alpha(0f)
                                    .setDuration(shortAnimationDuration.toLong())
                                    .setListener(null)
                            }
                        }
                    }
                }
            }
        )
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