package com.aventurine.marvel.mosby

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.BackstackAccessor
import androidx.fragment.app.Fragment
import com.hannesdorfmann.mosby3.mvp.MvpView
import java.util.UUID

class FragmentMviDelegateImpl<V : MvpView, P : MviPresenter<V, *>> @JvmOverloads constructor(
    delegateCallback: MviDelegateCallback<V, P>,
    fragment: Fragment,
    keepPresenterDuringScreenOrientationChange: Boolean = true,
    keepPresenterOnBackstack: Boolean = true
) :
    FragmentMviDelegate<V, P> {
    private var mosbyViewId: String? = null
    private var delegateCallback: MviDelegateCallback<V, P>? = null
    private var fragment: Fragment? = null
    private var onViewCreatedCalled = false
    private var keepPresenterDuringScreenOrientationChange = false
    private var keepPresenterOnBackstack = false
    private var presenter: P? = null
    private var viewStateWillBeRestored = false

    init {
        if (delegateCallback == null) {
            throw NullPointerException("delegateCallback == null")
        } else if (fragment == null) {
            throw NullPointerException("fragment == null")
        } else require(!(!keepPresenterDuringScreenOrientationChange && keepPresenterOnBackstack)) { "It is not possible to keep the presenter on backstack, but NOT keep presenter through screen orientation changes. Keep presenter on backstack also requires keep presenter through screen orientation changes to be enabled" }
        this.delegateCallback = delegateCallback
        this.fragment = fragment
        this.keepPresenterDuringScreenOrientationChange = keepPresenterDuringScreenOrientationChange
        this.keepPresenterOnBackstack = keepPresenterOnBackstack
    }

    override fun onCreate(saved: Bundle?) {
        if ((keepPresenterDuringScreenOrientationChange || keepPresenterOnBackstack) && saved != null) {
            mosbyViewId = saved.getString("com.hannesdorfmann.mosby3.fragment.mvi.id")
        }
        if (DEBUG) {
            Log.d(
                "FragmentMviDelegateImpl",
                "MosbyView ID = " + mosbyViewId + " for MvpView: " + delegateCallback!!.mvpView
            )
        }
        if (mosbyViewId == null) {
            presenter = createViewIdAndCreatePresenter()
            viewStateWillBeRestored = false
            if (DEBUG) {
                Log.d("FragmentMviDelegateImpl", "new Presenter instance created: " + presenter)
            }
        } else {
            presenter =
                PresenterManager.getPresenter<P>(activity, mosbyViewId!!)
            if (presenter == null) {
                presenter = createViewIdAndCreatePresenter()
                viewStateWillBeRestored = false
                if (DEBUG) {
                    Log.d(
                        "FragmentMviDelegateImpl",
                        "No Presenter instance found in cache, although MosbyView ID present. This was caused by process death, therefore new Presenter instance created: " + presenter
                    )
                }
            } else {
                viewStateWillBeRestored = true
                if (DEBUG) {
                    Log.d(
                        "FragmentMviDelegateImpl",
                        "Presenter instance reused from internal cache: " + presenter
                    )
                }
            }
        }
        checkNotNull(presenter) { "Oops, Presenter is null. This seems to be a Mosby internal bug. Please report this issue here: https://github.com/sockeqwe/mosby/issues" }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewCreatedCalled = true
    }

    override fun onStart() {
        val view: V? = delegateCallback!!.mvpView
        if (view == null) {
            throw NullPointerException("MvpView returned from getMvpView() is null. Returned by " + fragment)
        } else checkNotNull(presenter) { "Oops, Presenter is null. This seems to be a Mosby internal bug. Please report this issue here: https://github.com/sockeqwe/mosby/issues" }
        if (viewStateWillBeRestored) {
            delegateCallback!!.setRestoringViewState(true)
        }
        presenter!!.attachView(view)
        if (viewStateWillBeRestored) {
            delegateCallback!!.setRestoringViewState(false)
        }
        if (DEBUG) {
            Log.d(
                "FragmentMviDelegateImpl",
                "MvpView attached to Presenter. MvpView: " + view + "   Presenter: " + presenter
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        check(onViewCreatedCalled) { "It seems that onCreateView() has never been called (or has returned null). This means that your fragment is headless (no UI). That is not allowed because it doesn't make sense to use Mosby with a Fragment without View." }
    }

    private fun retainPresenterInstance(
        keepPresenterOnBackstack: Boolean,
        activity: Activity,
        fragment: Fragment?
    ): Boolean {
        return if (activity.isChangingConfigurations) {
            keepPresenterDuringScreenOrientationChange
        } else if (activity.isFinishing) {
            false
        } else if (keepPresenterOnBackstack && BackstackAccessor.isFragmentOnBackStack(fragment)) {
            true
        } else {
            !fragment!!.isRemoving
        }
    }

    override fun onDestroyView() {
        onViewCreatedCalled = false
    }

    override fun onStop() {
        presenter!!.detachView()
        viewStateWillBeRestored = true
        if (DEBUG) {
            Log.d(
                "FragmentMviDelegateImpl",
                "detached MvpView from Presenter. MvpView " + delegateCallback!!.mvpView + "   Presenter: " + presenter
            )
        }
    }

    override fun onDestroy() {
        val activity = activity
        val retainPresenterInstance = retainPresenterInstance(
            keepPresenterOnBackstack, activity,
            fragment
        )
        if (!retainPresenterInstance) {
            presenter!!.destroy()
            if (mosbyViewId != null) {
                PresenterManager.remove(activity, mosbyViewId!!)
            }
            if (DEBUG) {
                Log.d("FragmentMviDelegateImpl", "Presenter destroyed")
            }
        } else if (DEBUG) {
            Log.d(
                "FragmentMviDelegateImpl",
                "Retaining presenter instance: " + java.lang.Boolean.toString(
                    retainPresenterInstance
                ) + " " + presenter
            )
        }
        presenter = null
        delegateCallback = null
        fragment = null
    }

    override fun onPause() {}
    override fun onResume() {}
    private val activity: Activity
        private get() {
            val activity: Activity? = fragment!!.activity
            return activity
                ?: throw NullPointerException("Activity returned by Fragment.getActivity() is null. Fragment is " + fragment)
        }

    private fun createViewIdAndCreatePresenter(): P {
        val presenter: P? = delegateCallback!!.createPresenter()
        return if (presenter == null) {
            throw NullPointerException("Presenter returned from createPresenter() is null. Fragment is " + fragment)
        } else {
            if (keepPresenterDuringScreenOrientationChange || keepPresenterOnBackstack) {
                val activity = activity
                mosbyViewId = UUID.randomUUID().toString()
                PresenterManager.putPresenter(activity, mosbyViewId!!, presenter)
            }
            presenter
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if ((keepPresenterDuringScreenOrientationChange || keepPresenterOnBackstack) && outState != null) {
            outState.putString("com.hannesdorfmann.mosby3.fragment.mvi.id", mosbyViewId)
            retainPresenterInstance(keepPresenterOnBackstack, activity, fragment)
            if (DEBUG) {
                Log.d(
                    "FragmentMviDelegateImpl",
                    "Saving MosbyViewId into Bundle. ViewId: " + mosbyViewId
                )
            }
        }
    }

    override fun onAttach(activity: Activity) {}
    override fun onDetach() {}
    override fun onAttach(context: Context) {}
    override fun onAttachFragment(childFragment: Fragment) {}
    override fun onConfigurationChanged(newConfig: Configuration) {}

    companion object {
        var DEBUG = false
        private const val DEBUG_TAG = "FragmentMviDelegateImpl"
        private const val KEY_MOSBY_VIEW_ID = "com.hannesdorfmann.mosby3.fragment.mvi.id"
    }
}