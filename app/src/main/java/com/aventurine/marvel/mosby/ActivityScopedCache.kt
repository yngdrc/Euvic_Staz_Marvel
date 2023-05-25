package com.aventurine.marvel.mosby

import android.util.ArrayMap
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

class ActivityScopedCache {
    private val presenterMap: MutableMap<String, PresenterHolder> = ArrayMap()
    fun clear() {
        /*
    // TODO: can this check if there are still Presenters in the internal cache that must be detached? Maybe post() / postDelayed() on the  MainThreadLooper()
    // it doesnt work out that well, because super.onDestroy() which then invokes PresenterManager.clear() might be called before the MviDelegates did their job to remove the Presenter from cache and detach the view permanently

    for (PresenterHolder holder : presenterMap.values()) {
      // This should never be the case: If there were some presenters left in the internal cache,
      // a delegate didn't work correctly as expected
      if (holder.presenter != null) {
        holder.presenter.detachView(false);
        if (PresenterManager.DEBUG) {
          Log.w(PresenterManager.DEBUG_TAG,
              "Found a Presenter that is still alive. This should never happen. It seems that a MvpDelegate / MviDelegate didn't work correctly because this Delegate should have removed the presenter. The Presenter was "
                  + holder.presenter);
        }
      }
    }
    */
        presenterMap.clear()
    }

    /**
     * Get the Presenter for a given [MvpView] if exists or `null`
     *
     * @param viewId The mosby internal view id
     * @param <P> The type tof the [MvpPresenter]
     * @return The Presenter for the given view id or `null`
    </P> */

    fun <P> getPresenter(viewId: String): P? {
        val holder = presenterMap[viewId]
        return if (holder == null) null else holder.presenter as P?
    }

    /**
     * Get the ViewState for a given [MvpView] if exists or `null`
     *
     * @param viewId The mosby internal view id
     * @param <VS> The type tof the [MvpPresenter]
     * @return The ViewState for the given view id or `null`
    </VS> */

    fun <VS> getViewState(viewId: String): VS? {
        val holder = presenterMap[viewId]
        return if (holder == null) null else holder.viewState as VS?
    }

    /**
     * Put the presenter in the internal cache
     *
     * @param viewId The mosby internal View id of the [MvpView] which the presenter is
     * associated to.
     * @param presenter The Presenter
     */
    fun putPresenter(
        viewId: String,
        presenter: MvpPresenter<out MvpView?>
    ) {
        if (viewId == null) {
            throw NullPointerException("ViewId is null")
        }
        if (presenter == null) {
            throw NullPointerException("Presenter is null")
        }
        var presenterHolder = presenterMap[viewId]
        if (presenterHolder == null) {
            presenterHolder = PresenterHolder()
            presenterHolder.presenter = presenter
            presenterMap[viewId] = presenterHolder
        } else {
            presenterHolder.presenter = presenter
        }
    }

    /**
     * Put the viewstate in the internal cache
     *
     * @param viewId The mosby internal View id of the [MvpView] which the presenter is
     * associated to.
     * @param viewState The Viewstate
     */
    fun putViewState(
        viewId: String,
        viewState: Any
    ) {
        if (viewId == null) {
            throw NullPointerException("ViewId is null")
        }
        if (viewState == null) {
            throw NullPointerException("ViewState is null")
        }
        var presenterHolder = presenterMap[viewId]
        if (presenterHolder == null) {
            presenterHolder = PresenterHolder()
            presenterHolder.viewState = viewState
            presenterMap[viewId] = presenterHolder
        } else {
            presenterHolder.viewState = viewState
        }
    }

    /**
     * Removes the Presenter (and ViewState) from the internal storage
     *
     * @param viewId The msoby internal view id
     */
    fun remove(viewId: String) {
        if (viewId == null) {
            throw NullPointerException("View Id is null")
        }
        presenterMap.remove(viewId)
    }

    /**
     * Internal config change Cache entry
     */
    internal class PresenterHolder {
        var presenter: MvpPresenter<*>? = null
        var viewState // workaround: didn't want to introduce dependency to viewstate module
                : Any? = null
    }
}
