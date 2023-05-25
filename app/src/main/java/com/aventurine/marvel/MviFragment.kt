package com.aventurine.marvel

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.hannesdorfmann.mosby3.mvp.MvpView

abstract class MviFragment<V : MvpView, P : MviPresenter<V, *>> :
    Fragment(), MvpView, MviDelegateCallback<V, P> {
    override val mvpView: V
        get() {
            return try {
                this as V
            } catch (e: ClassCastException) {
                val msg =
                    "Couldn't cast the View to the corresponding View interface. Most likely you forgot to add \"Fragment implements YourMvpViewInterface\".\""
                Log.e(this.toString(), msg)
                throw RuntimeException(msg, e)
            }
        }
    private var isRestoringViewState = false
    protected var mvpDelegate: FragmentMviDelegate<V, P>? = null
        get() {
            if (field == null) {
                field = FragmentMviDelegateImpl(this, this)
            }
            return field
        }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mvpDelegate?.onCreate(savedInstanceState)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        mvpDelegate?.onDestroy()
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvpDelegate?.onSaveInstanceState(outState)
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        mvpDelegate?.onPause()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        mvpDelegate?.onResume()
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        mvpDelegate?.onStart()
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        mvpDelegate?.onStop()
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mvpDelegate?.onViewCreated(view, savedInstanceState)
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        mvpDelegate?.onDestroyView()
    }

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mvpDelegate?.onActivityCreated(savedInstanceState)
    }

    @CallSuper
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mvpDelegate?.onAttach(activity)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mvpDelegate?.onAttach(context)
    }

    @CallSuper
    override fun onDetach() {
        super.onDetach()
        mvpDelegate?.onDetach()
    }

    @CallSuper
    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        mvpDelegate?.onAttachFragment(childFragment)
    }

    @CallSuper
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mvpDelegate?.onConfigurationChanged(newConfig)
    }

    /**
     * Instantiate a presenter instance
     *
     * @return The [MvpPresenter] for this viewState
     */

    abstract override fun createPresenter(): P

    /**
     * Get the mvp delegate. This is internally used for creating presenter, attaching and detaching
     * viewState from presenter.
     *
     *
     * **Please note that only one instance of mvp delegate should be used per Fragment
     * instance**.
     *
     *
     *
     *
     * Only override this method if you really know what you are doing.
     *
     *
     * @return [FragmentMviDelegate]
     */

    override fun setRestoringViewState(restoringViewState: Boolean) {
        isRestoringViewState = restoringViewState
    }

    protected fun isRestoringViewState(): Boolean {
        return isRestoringViewState
    }
}