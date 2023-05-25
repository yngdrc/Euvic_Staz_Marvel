package com.aventurine.marvel.main

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.aventurine.R
import com.aventurine.marvel.utils.Constants.Companion.MAIN_LAYOUT_ID
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent


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