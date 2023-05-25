package com.aventurine.marvel

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hannesdorfmann.mosby3.mvp.MvpView

interface FragmentMviDelegate<V : MvpView, P : MviPresenter<V, *>> {
    /**
     * Must be called from [Fragment.onCreate]
     *
     * @param saved The bundle
     */
    fun onCreate(saved: Bundle?)

    /**
     * Must be called from [Fragment.onDestroy]
     */
    fun onDestroy()

    /**
     * Must be called from [Fragment.onViewCreated]
     *
     * @param view The inflated view
     * @param savedInstanceState the bundle with the viewstate
     */
    fun onViewCreated(view: View, savedInstanceState: Bundle?)

    /**
     * Must be called from [Fragment.onDestroyView]
     */
    fun onDestroyView()

    /**
     * Must be called from [Fragment.onPause]
     */
    fun onPause()

    /**
     * Must be called from [Fragment.onResume]
     */
    fun onResume()

    /**
     * Must be called from [Fragment.onStart]
     */
    fun onStart()

    /**
     * Must be called from [Fragment.onStop]
     */
    fun onStop()

    /**
     * Must be called from [Fragment.onActivityCreated]
     *
     * @param savedInstanceState The saved bundle
     */
    fun onActivityCreated(savedInstanceState: Bundle?)

    /**
     * Must be called from [Fragment.onAttach]
     *
     * @param activity The activity the fragment is attached to
     */
    fun onAttach(activity: Activity)

    /**
     * Must be called from [Fragment.onAttach]
     *
     * @param context The context the fragment is attached to
     */
    fun onAttach(context: Context)

    /**
     * Must be called from [Fragment.onDetach]
     */
    fun onDetach()

    /**
     * Must be called from [Fragment.onSaveInstanceState]
     */
    fun onSaveInstanceState(outState: Bundle)

    /**
     * Must be called from [Fragment.onAttachFragment]
     *
     * @param childFragment the new childFragment
     */
    fun onAttachFragment(childFragment: Fragment)

    /**
     * Must be called from [Fragment.onConfigurationChanged]
     *
     * @param newConfig The new config
     */
    fun onConfigurationChanged(newConfig: Configuration)
}