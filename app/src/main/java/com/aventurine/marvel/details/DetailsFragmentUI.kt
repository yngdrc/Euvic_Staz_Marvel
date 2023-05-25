package com.aventurine.marvel.details

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import androidx.recyclerview.widget.PagerSnapHelper
import com.aventurine.R


class DetailsFragmentUI(private val seriesAdapter: SeriesAdapter) : AnkoComponent<DetailsFragment> {
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var characterName: TextView
    lateinit var characterImage: ImageView
    lateinit var characterDescription: TextView
    lateinit var rv: RecyclerView
    lateinit var seriesProgressBar: ProgressBar
    lateinit var seriesInfo: TextView
    lateinit var backgroundImage: ImageView
    override fun createView(ui: AnkoContext<DetailsFragment>) = ui.apply {
        swipeRefreshLayout = swipeRefreshLayout() {
            linearLayout() {
                lparams(matchParent, matchParent)
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_HORIZONTAL

                // character's image and name
                relativeLayout() {
                    setBackgroundColor(Color.BLACK)
                    lparams(matchParent, wrapContent)
                    backgroundImage = imageView() {
                        alpha = 0.2f
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }.lparams(matchParent, wrapContent)
                    linearLayout() {
                        gravity = Gravity.CENTER_HORIZONTAL
                        orientation = LinearLayout.VERTICAL
                        characterImage = imageView() {
                            foreground = ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.container_stroke,
                                null
                            )
                        }.lparams(matchParent, wrapContent)
                        characterName = textView() {
                            textColor = Color.WHITE
                            topPadding = dip(8)
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams(wrapContent, wrapContent)
                    }.lparams(wrapContent, wrapContent) {
                        centerInParent()
                    }
                }.lparams(matchParent, wrapContent)

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
                    topMargin = dip(16)
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
                        rv = recyclerView {
                            backgroundColor = Color.WHITE
                            layoutManager =
                                LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
                            adapter = seriesAdapter
                            PagerSnapHelper().attachToRecyclerView(this)
                            overScrollMode = View.OVER_SCROLL_NEVER
                        }.lparams(matchParent, wrapContent)
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