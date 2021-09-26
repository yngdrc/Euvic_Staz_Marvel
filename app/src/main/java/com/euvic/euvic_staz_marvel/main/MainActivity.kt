package com.euvic.euvic_staz_marvel.main

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.euvic.euvic_staz_marvel.R
import com.euvic.euvic_staz_marvel.db.CharactersDao
import com.euvic.euvic_staz_marvel.db.CharactersDatabase
import com.euvic.euvic_staz_marvel.db.CharactersRepo
import com.euvic.euvic_staz_marvel.utils.Constants.Companion.MAIN_LAYOUT_ID
import org.jetbrains.anko.ctx
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent


class MainActivity : AppCompatActivity() {
    lateinit var mainContainer: LinearLayout
    private lateinit var finalHost: NavHostFragment

    lateinit var charactersDatabase: CharactersDatabase
    lateinit var charactersDao: CharactersDao
    lateinit var charactersRepo: CharactersRepo

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
        charactersDatabase = CharactersDatabase.getDatabase(this)
        charactersDao = charactersDatabase.charactersDao()
        charactersRepo = CharactersRepo(charactersDao)

        // sets the navigation host fragment to fragment container
        finalHost = NavHostFragment.create(R.navigation.fragments_navigation)
        supportFragmentManager.beginTransaction()
                .replace(mainContainer.id, finalHost)
                .setPrimaryNavigationFragment(finalHost)
                .commit()
    }
}