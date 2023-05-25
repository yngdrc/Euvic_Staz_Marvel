package com.aventurine.marvel

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import androidx.annotation.MainThread
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import java.util.UUID

class PresenterManager private constructor() {
    init {
        throw RuntimeException("Not instantiatable!")
    }

    companion object {
        var DEBUG = false
        const val DEBUG_TAG = "PresenterManager"
        const val KEY_ACTIVITY_ID = "com.hannesdorfmann.mosby3.MosbyPresenterManagerActivityId"
        private val activityIdMap: MutableMap<Activity, String> = ArrayMap()
        private val activityScopedCacheMap: MutableMap<String, ActivityScopedCache> = ArrayMap()
        val activityLifecycleCallbacks: ActivityLifecycleCallbacks =
            object : ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    if (savedInstanceState != null) {
                        val activityId = savedInstanceState.getString(KEY_ACTIVITY_ID)
                        if (activityId != null) {
                            // After a screen orientation change we map the newly created Activity to the same
                            // Activity ID as the previous activity has had (before screen orientation change)
                            activityIdMap[activity] = activityId
                        }
                    }
                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                    // Save the activityId into bundle so that the other
                    val activityId = activityIdMap[activity]
                    if (activityId != null) {
                        outState.putString(KEY_ACTIVITY_ID, activityId)
                    }
                }

                override fun onActivityStarted(activity: Activity) {}
                override fun onActivityResumed(activity: Activity) {}
                override fun onActivityPaused(activity: Activity) {}
                override fun onActivityStopped(activity: Activity) {}
                override fun onActivityDestroyed(activity: Activity) {
                    if (!activity.isChangingConfigurations) {
                        // Activity will be destroyed permanently, so reset the cache
                        val activityId = activityIdMap[activity]
                        if (activityId != null) {
                            val scopedCache = activityScopedCacheMap[activityId]
                            if (scopedCache != null) {
                                scopedCache.clear()
                                activityScopedCacheMap.remove(activityId)
                            }

                            // No Activity Scoped cache available, so unregister
                            if (activityScopedCacheMap.isEmpty()) {
                                // All Mosby related activities are destroyed, so we can remove the activity lifecylce listener
                                activity.application
                                    .unregisterActivityLifecycleCallbacks(this)
                                if (DEBUG) {
                                    Log.d(DEBUG_TAG, "Unregistering ActivityLifecycleCallbacks")
                                }
                            }
                        }
                    }
                    activityIdMap.remove(activity)
                }
            }

        /**
         * Get an already existing [ActivityScopedCache] or creates a new one if not existing yet
         *
         * @param activity The Activitiy for which you want to get the activity scope for
         * @return The [ActivityScopedCache] for the given Activity
         */

        @MainThread
        fun getOrCreateActivityScopedCache(
            activity: Activity
        ): ActivityScopedCache {
            if (activity == null) {
                throw NullPointerException("Activity is null")
            }
            var activityId = activityIdMap[activity]
            if (activityId == null) {
                // Activity not registered yet
                activityId = UUID.randomUUID().toString()
                activityIdMap[activity] = activityId
                if (activityIdMap.size == 1) {
                    // Added the an Activity for the first time so register Activity LifecycleListener
                    activity.application.registerActivityLifecycleCallbacks(
                        activityLifecycleCallbacks
                    )
                    if (DEBUG) {
                        Log.d(DEBUG_TAG, "Registering ActivityLifecycleCallbacks")
                    }
                }
            }
            var activityScopedCache = activityScopedCacheMap[activityId]
            if (activityScopedCache == null) {
                activityScopedCache = ActivityScopedCache()
                activityScopedCacheMap[activityId] = activityScopedCache
            }
            return activityScopedCache
        }

        /**
         * Get the  [ActivityScopedCache] for the given Activity or `null` if no [ ] exists for the given Activity
         *
         * @param activity The activity
         * @return The [ActivityScopedCache] or null
         * @see .getOrCreateActivityScopedCache
         */

        @MainThread
        fun getActivityScope(activity: Activity): ActivityScopedCache? {
            if (activity == null) {
                throw NullPointerException("Activity is null")
            }
            val activityId = activityIdMap[activity] ?: return null
            return activityScopedCacheMap[activityId]
        }

        /**
         * Get the presenter for the View with the given (Mosby - internal) view Id or `null`
         * if no presenter for the given view (via view id) exists.
         *
         * @param activity The Activity (used for scoping)
         * @param viewId The mosby internal View Id (unique among all [MvpView]
         * @param <P> The Presenter type
         * @return The Presenter or `null`
        </P> */

        fun <P> getPresenter(activity: Activity, viewId: String): P? {
            if (activity == null) {
                throw NullPointerException("Activity is null")
            }
            if (viewId == null) {
                throw NullPointerException("View id is null")
            }
            val scopedCache = getActivityScope(activity)
            return if (scopedCache == null) null else scopedCache.getPresenter<Any>(viewId) as P?
        }

        /**
         * Get the ViewState (see mosby viestate modlue) for the View with the given (Mosby - internal)
         * view Id or `null`
         * if no viewstate for the given view exists.
         *
         * @param activity The Activity (used for scoping)
         * @param viewId The mosby internal View Id (unique among all [MvpView]
         * @param <VS> The type of the ViewState type
         * @return The Presenter or `null`
        </VS> */

        fun <VS> getViewState(activity: Activity, viewId: String): VS? {
            if (activity == null) {
                throw NullPointerException("Activity is null")
            }
            if (viewId == null) {
                throw NullPointerException("View id is null")
            }
            val scopedCache = getActivityScope(activity)
            return if (scopedCache == null) null else scopedCache.getViewState<Any>(viewId) as VS?
        }

        /**
         * Get the Activity of a context. This is typically used to determine the hosting activity of a
         * [View]
         *
         * @param context The context
         * @return The Activity or throws an Exception if Activity couldnt be determined
         */

        fun getActivity(context: Context): Activity {
            var context: Context? = context ?: throw NullPointerException("context == null")
            if (context is Activity) {
                return context
            }
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }
                context = context.baseContext
            }
            throw IllegalStateException("Could not find the surrounding Activity")
        }

        /**
         * Clears the internal (static) state. Used for testing.
         */
        fun reset() {
            activityIdMap.clear()
            for (scopedCache in activityScopedCacheMap.values) {
                scopedCache.clear()
            }
            activityScopedCacheMap.clear()
        }

        /**
         * Puts the presenter into the internal cache
         *
         * @param activity The parent activity
         * @param viewId the view id (mosby internal)
         * @param presenter the presenter
         */
        fun putPresenter(
            activity: Activity, viewId: String,
            presenter: MvpPresenter<out MvpView?>
        ) {
            if (activity == null) {
                throw NullPointerException("Activity is null")
            }
            val scopedCache = getOrCreateActivityScopedCache(activity)
            scopedCache.putPresenter(viewId!!, presenter!!)
        }

        /**
         * Puts the presenter into the internal cache
         *
         * @param activity The parent activity
         * @param viewId the view id (mosby internal)
         * @param viewState the presenter
         */
        fun putViewState(
            activity: Activity, viewId: String,
            viewState: Any
        ) {
            if (activity == null) {
                throw NullPointerException("Activity is null")
            }
            val scopedCache = getOrCreateActivityScopedCache(activity)
            scopedCache.putViewState(viewId!!, viewState!!)
        }

        /**
         * Removes the Presenter (and ViewState) for the given View. Does nothing if no Presenter is
         * stored internally with the given viewId
         *
         * @param activity The activity
         * @param viewId The mosby internal view id
         */
        fun remove(activity: Activity, viewId: String) {
            if (activity == null) {
                throw NullPointerException("Activity is null")
            }
            val activityScope = getActivityScope(activity)
            activityScope?.remove(viewId!!)
        }
    }
}