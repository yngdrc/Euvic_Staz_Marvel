package com.aventurine.marvel.main

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.aventurine.R


class MainActivity : AppCompatActivity() {
    private lateinit var layout: LinearLayout
    private val navHostFragment: NavHostFragment = NavHostFragment.create(R.navigation.fragments_navigation)

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContentView(
            MainActivityLayout(context = this.application).also {
                supportFragmentManager.beginTransaction()
                    .replace(it.fragmentContainer.id, navHostFragment)
                    .setPrimaryNavigationFragment(navHostFragment)
                    .commit()
                layout = it
            }
        )
    }
}