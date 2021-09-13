package com.euvic.euvic_staz_marvel.main

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.euvic.euvic_staz_marvel.apiservice.MarvelDatasource
import com.euvic.euvic_staz_marvel.models.CharactersDataClass
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import com.euvic.euvic_staz_marvel.characters.CharactersAdapter


class MainActivity : MviActivity<MainView, MainPresenter>(), MainView {
    lateinit var mainContainer: LinearLayout
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val frame = FrameLayout(this)
//        frame.id = MAIN_ACTIVITY_CONTAINER_ID
//        setContentView(
//            frame, ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
//            )
//        )
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction().add(MAIN_ACTIVITY_CONTAINER_ID, CharactersFragment()).commit()
//        }
    }

    override fun createPresenter(): MainPresenter = MainPresenter()

    override fun getCharacters(): Observable<CharactersDataClass> {
        return MarvelDatasource().getCharacters().subscribeOn(Schedulers.io())
    }

    override fun render(viewState: MainViewState) {
        if (viewState.loading) {
            Log.d("VIEW STATE", "LOADING")
        }
        if (viewState.characters!=null) {
            adapter = CharactersAdapter(viewState.characters!!.data.results)
            Log.d("VIEW STATE", "GOT CHARACTERS")
        }
    }
}