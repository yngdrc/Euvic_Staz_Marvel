package com.euvic.euvic_staz_marvel.details

import android.app.ProgressDialog.show
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.core.view.marginBottom
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.euvic.euvic_staz_marvel.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import androidx.recyclerview.widget.PagerSnapHelper

import androidx.recyclerview.widget.SnapHelper
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.support.v4.contentLoadingProgressBar


class DetailsFragmentUI(private val seriesAdapter: SeriesAdapter): AnkoComponent<DetailsFragment> {
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var characterName: TextView
    lateinit var characterImage: ImageView
    lateinit var characterDescription: TextView
    lateinit var rv: RecyclerView
    lateinit var seriesProgressBar: ProgressBar
    lateinit var seriesInfo: TextView
    override fun createView(ui: AnkoContext<DetailsFragment>) = ui.apply {
        swipeRefreshLayout = swipeRefreshLayout() {
            linearLayout() {
                topPadding = dip(32)
                lparams(matchParent, matchParent)
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_HORIZONTAL

                // character's image and name
                characterImage = imageView() {
                    foreground = resources.getDrawable(R.drawable.container_stroke)
                }.lparams(wrapContent, wrapContent)
                characterName = textView() {
                    textColor = Color.BLACK
                    topPadding = dip(8)
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams(wrapContent, wrapContent) {
                    bottomMargin = dip(16)
                }

                // description container
                linearLayout {
                    lparams(matchParent, wrapContent)
                    padding = dip(16)
                    orientation = LinearLayout.HORIZONTAL
                    imageView() {
                        setImageResource(R.drawable.ic_baseline_info_24)
                        setBackgroundResource(R.drawable.text_rounded)
                        padding = dip(4)
                    }.lparams(wrapContent, matchParent)
                    characterDescription = textView() {
                        textColor = Color.BLACK
                        padding = dip(8)
                        leftPadding = dip(16)
                        rightPadding = dip(16)
                        setBackgroundColor(Color.parseColor("#f9f9f9"))
                    }.lparams(matchParent, wrapContent)
                }.lparams(matchParent, wrapContent) {
                    bottomMargin = dip(8)
                    gravity = Gravity.LEFT
                }

                // series container
                linearLayout() {
                    seriesProgressBar = progressBar {
                    }.lparams(matchParent, wrapContent) {
                        height = 150
                    }
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
//                        textView {
//                            text = "Series:"
//                            gravity = Gravity.CENTER
//                            textColor = Color.WHITE
//                            setBackgroundResource(R.drawable.text_rounded)
//                            padding = dip(2)
//                        }.lparams(matchParent, wrapContent) {
//                            leftMargin = dip(8)
//                            rightMargin = dip(8)
//                        }
                        rv = recyclerView {
                            backgroundColor = Color.WHITE
                            layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
                            adapter = seriesAdapter
                            PagerSnapHelper().attachToRecyclerView(this)
                            overScrollMode = View.OVER_SCROLL_NEVER
                        }.lparams(matchParent, wrapContent) {
                        }
                        seriesInfo = textView() {
                            textColor = Color.BLACK
                            padding = dip(8)
                            leftPadding = dip(16)
                            rightPadding = dip(16)
                        }.lparams(matchParent, wrapContent)
                    }.lparams(matchParent, wrapContent)
                }
            }
        }
    }.view
}