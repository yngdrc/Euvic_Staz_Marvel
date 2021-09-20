package com.euvic.euvic_staz_marvel.main

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.euvic.euvic_staz_marvel.R
import com.euvic.euvic_staz_marvel.apiservice.MarvelDatasource
import com.euvic.euvic_staz_marvel.models.CharactersDataClass
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import com.euvic.euvic_staz_marvel.characters.CharactersAdapter
import com.euvic.euvic_staz_marvel.characters.CharactersFragment
import com.euvic.euvic_staz_marvel.characters.CharactersFragmentUI
import com.euvic.euvic_staz_marvel.utils.Constants.Companion.MAIN_LAYOUT_ID
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.support.v4.swipeRefreshLayout


class MainActivity : AppCompatActivity() {
    lateinit var mainContainer: LinearLayout
    private lateinit var finalHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainContainer = linearLayout {
            lparams(matchParent, matchParent)
            id = MAIN_LAYOUT_ID
        }
        setContentView(mainContainer, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        )

        // sets the navigation host fragment to fragment container
        finalHost = NavHostFragment.create(R.navigation.fragments_navigation)
        supportFragmentManager.beginTransaction()
                .replace(mainContainer.id, finalHost)
                .setPrimaryNavigationFragment(finalHost)
                .commit()
    }
}