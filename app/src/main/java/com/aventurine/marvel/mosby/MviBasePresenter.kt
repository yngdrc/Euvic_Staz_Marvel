package com.aventurine.marvel.mosby

import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import io.reactivex.rxjava3.subjects.UnicastSubject

/**
 * This type of presenter is responsible for interaction with the viewState in a Model-View-Intent way.
 * It is the bridge that is responsible for setting up the reactive flow between "view" and "model".
 *
 *
 *
 *
 * The methods [.bindIntents] and [.unbindIntents] are kind of representing the
 * lifecycle of this Presenter.
 *
 *  * [.bindIntents] is called the first time the view is attached
 *  * [.unbindIntents] is called once the view is detached permanently because the view
 * has been destroyed and hence this presenter is not needed anymore and will also be destroyed
 * afterwards too.
 *
 *
 *
 *
 *
 *
 * This means that a presenter can survive orientation changes. During orientation changes (or when
 * the view is put on the back stack because the user navigated to another view) the view
 * will be detached temporarily and reattached to the presenter afterwards. To avoid memory leaks
 * this Presenter class offers two methods:
 *
 *  * [.intent]: Use this to bind an Observable intent from the view
 *  * [.subscribeViewState]: Use this to bind the ViewState
 * (a viewState is an object (typically a POJO) that holds all the data the view needs to display
 *
 *
 *
 * By using [.intent] and [.subscribeViewState]
 * a relay will be established between the view and this presenter that allows the view to be
 * temporarily detached, without unsubscribing the underlying reactive business logic workflow and
 * without causing memory leaks (caused by recreation of the view).
 *
 *
 *
 *
 *
 * Please note that the methods [.attachView] and [.detachView]
 * should not be overridden unless you have a really good reason to do so. Usually [.bindIntents]
 * and [.unbindIntents] should be enough.
 *
 *
 *
 *
 *
 * In very rare cases you could also use [.getViewStateObservable] to offer an observable
 * to other components you can make this method public.
 *
 *
 *
 *
 *
 * **Please note that you should not reuse an MviBasePresenter once the View that originally has
 * instantiated this Presenter has been destroyed permanently**. App-wide singletons for
 * Presenters is not a good idea in Model-View-Intent. Reusing singleton-scoped Presenters for
 * different view instances may cause emitting the previous state of the previous attached view
 * (which already has been destroyed permanently).
 *
 *
 * @param <V>  The type of the view this presenter responds to
 * @param <VS> The type of the viewState state
 * @author Hannes Dorfmann
 * @since 3.0
</VS></V> */
abstract class MviBasePresenter<V : MvpView, VS: Any> : MviPresenter<V, VS> {
    /**
     * The binder is responsible for binding a single view intent.
     * Typically, you use that in [.bindIntents] in combination with the [ ][.intent] function like this:
     * <pre>`
     * Observable<Boolean> loadIntent = intent(new ViewIntentBinder() {
     * @Override
     * public Observable<Boolean> bind(MyView view){
     * return view.loadIntent();
     * }
     * }
    </Boolean></Boolean>`</pre> *
     *
     * @param <V> The View type
     * @param <I> The type of the Intent
    </I></V> */
    protected interface ViewIntentBinder<V : MvpView, I : Any> {
        fun bind(view: V): Observable<I>
    }

    /**
     * This "binder" is responsible for binding the view state to the currently attached view.
     * This typically "renders" the view.
     *
     *
     * Typically, this is used in [.bindIntents] with
     * [.subscribeViewState] like this:
     * <pre>`
     * Observable<MyViewState> viewState =  ... ;
     * subscribeViewStateConsumerActually(viewState, new ViewStateConsumer() {
     * @Override
     * public void accept(MyView view, MyViewState viewState){
     * view.render(viewState);
     * }
     * }
    </MyViewState>`</pre> *
     *
     * @param <V>  The view type
     * @param <VS> The ViewState type
    </VS></V> */
    protected interface ViewStateConsumer<V : MvpView, VS> {
        fun accept(view: V, viewState: VS)
    }

    /**
     * A simple class that holds a pair of the Intent relay and the binder to bind the actual Intent
     * Observable.
     *
     * @param <I> The Intent type
    </I> */
    private inner class IntentRelayBinderPair<I : Any>(
        val intentRelaySubject: Subject<I>,
        val intentBinder: ViewIntentBinder<V, I>
    )

    /**
     * This relay is the bridge to the viewState (UI). Whenever the viewState gets re-attached, the
     * latest state will be re-emitted.
     */
    private val viewStateBehaviorSubject: BehaviorSubject<VS>

    /**
     * We only allow to call [.subscribeViewState] method once
     */
    private var subscribeViewStateMethodCalled = false

    /**
     * List of internal relays, bridging the gap between intents coming from the viewState (will be
     * unsubscribed temporarily when viewState is detached i.e. during config changes)
     */
    private val intentRelaysBinders: MutableList<IntentRelayBinderPair<*>> = ArrayList(4)

    /**
     * Composite Disposables holding subscriptions to all intents observable offered by the viewState.
     */
    private var intentDisposables: CompositeDisposable? = null

    /**
     * Disposable to unsubscribe from the viewState when the viewState is detached (i.e. during screen
     * orientation changes)
     */
    private var viewRelayConsumerDisposable: Disposable? = null

    /**
     * Disposable between the viewState observable returned from [.intent]
     * and [.viewStateBehaviorSubject]
     */
    private var viewStateDisposable: Disposable? = null

    /**
     * Used to determine whether or not a View has been attached for the first time.
     * This is used to determine whether or not the intents should be bound via [ ][.bindIntents] or rebound internally.
     */
    private var viewAttachedFirstTime = true

    /**
     * This binder is used to subscribe the view's render method to render the ViewState in the view.
     */
    private var viewStateConsumer: ViewStateConsumer<V, VS>? = null

    /**
     * Creates a new Presenter without an initial view state
     */
    constructor() {
        viewStateBehaviorSubject = BehaviorSubject.create()
        reset()
    }

    /**
     * Creates a new Presenter with the initial view state
     *
     * @param initialViewState initial view state (must be not null)
     */
    constructor(initialViewState: VS?) {
        if (initialViewState == null) {
            throw NullPointerException("Initial ViewState == null")
        }
        viewStateBehaviorSubject = BehaviorSubject.createDefault(initialViewState)
        reset()
    }

    /**
     * Gets the view state observable.
     *
     *
     * Most likely you will use this method for unit testing your presenter.
     *
     *
     *
     *
     *
     * In some very rare case it could be useful to provide other components, such as other presenters,
     * access to the state. This observable contains the same value as the one from [ ][.subscribeViewState] which is also used to render the view.
     * In other words, this observable also represents the state of the View, so you could subscribe
     * via this observable to the view's state.
     *
     *
     * @return Observable
     */
    protected val viewStateObservable: Observable<VS>
        get() = viewStateBehaviorSubject

    @CallSuper
    override fun attachView(view: V) {
        if (viewAttachedFirstTime) {
            bindIntents()
        }

        //
        // Build the chain from bottom to top:
        // 1. Subscribe to ViewState
        // 2. Subscribe intents
        //
        if (viewStateConsumer != null) {
            subscribeViewStateConsumerActually(view)
        }
        val intentsSize = intentRelaysBinders.size
        for (i in 0 until intentsSize) {
            val intentRelayBinderPair = intentRelaysBinders[i]
            bindIntentActually<Any>(view, intentRelayBinderPair)
        }
        viewAttachedFirstTime = false
    }

    @CallSuper
    override fun detachView() {
        detachView(true)
        if (viewRelayConsumerDisposable != null) {
            // Cancel subscription from View to viewState Relay
            viewRelayConsumerDisposable!!.dispose()
            viewRelayConsumerDisposable = null
        }
        if (intentDisposables != null) {
            // Cancel subscriptions from view intents to intent Relays
            intentDisposables!!.dispose()
            intentDisposables = null
        }
    }

    @CallSuper
    override fun destroy() {
        detachView(false)
        if (viewStateDisposable != null) {
            // Cancel the overall observable stream
            viewStateDisposable!!.dispose()
        }
        unbindIntents()
        reset()
        // TODO should we re-emit the initial state? What if no initial state has been set?
        // TODO should we rather throw an exception if presenter is reused after view has been detached permanently
    }

    /**
     * {@inheritDoc}
     */
    @Deprecated("")
    @CallSuper
    override fun detachView(retainInstance: Boolean) {
    }

    /**
     * This is called when the View has been detached permanently (view is destroyed permanently)
     * to reset the internal state of this Presenter to be ready for being reused (even though
     * reusing presenters after their view has been destroy is BAD)
     */
    private fun reset() {
        viewAttachedFirstTime = true
        intentRelaysBinders.clear()
        subscribeViewStateMethodCalled = false
    }

    /**
     * This method subscribes the Observable emitting `ViewState` over time to the passed
     * consumer.
     * **Only invoke this method once! Typically, in [.bindIntents]**
     *
     *
     * Internally, Mosby will hold some relays to ensure that no items emitted from the ViewState
     * Observable will be lost while viewState is not attached nor that the subscriptions to
     * viewState intents will cause memory leaks while viewState detached.
     *
     *
     *
     * Typically, this method is used in [.bindIntents]  like this:
     * <pre>`
     * Observable<MyViewState> viewState =  ... ;
     * subscribeViewStateConsumerActually(viewState, new ViewStateConsumer() {
     * @Override
     * public void accept(MyView view, MyViewState viewState){
     * view.render(viewState);
     * }
     * }
    </MyViewState>`</pre> *
     *
     * @param viewStateObservable The Observable emitting new ViewState. Typically, an intent [                            ][.intent] causes the underlying business logic to do a change and eventually
     * create a new ViewState.
     * @param consumer            [ViewStateConsumer] The consumer that will update ("render") the view.
     */
    @MainThread
    protected fun subscribeViewState(
        viewStateObservable: Observable<VS>,
        consumer: ViewStateConsumer<V, VS>
    ) {
        check(!subscribeViewStateMethodCalled) { "subscribeViewState() method is only allowed to be called once" }
        subscribeViewStateMethodCalled = true
        if (viewStateObservable == null) {
            throw NullPointerException("ViewState Observable is null")
        }
        if (consumer == null) {
            throw NullPointerException("ViewStateBinder is null")
        }
        viewStateConsumer = consumer
        viewStateDisposable = viewStateObservable.subscribeWith(
            DisposableViewStateObserver(viewStateBehaviorSubject)
        )
    }

    /**
     * Actually subscribes the view as consumer to the internally view relay.
     *
     * @param view The mvp view
     */
    @MainThread
    private fun subscribeViewStateConsumerActually(view: V) {
        if (view == null) {
            throw NullPointerException("View is null")
        }
        if (viewStateConsumer == null) {
            throw NullPointerException(
                ViewStateConsumer::class.java.simpleName
                        + " is null. This is a Mosby internal bug. Please file an issue at https://github.com/sockeqwe/mosby/issues"
            )
        }
        viewRelayConsumerDisposable = viewStateBehaviorSubject.subscribe { vs ->
            viewStateConsumer!!.accept(
                view,
                vs
            )
        }
    }

    /**
     * This method is called once the view is attached to this presenter for the very first time.
     * For instance, it will not be called again during screen orientation changes when the view will be
     * detached temporarily.
     *
     *
     *
     *
     * The counter part of this method is [.unbindIntents].
     * This [.bindIntents] and [.unbindIntents] are kind of representing the
     * lifecycle of this Presenter.
     * [.bindIntents] is called the first time the view is attached
     * and [.unbindIntents] is called once the view is detached permanently because it has
     * been destroyed and hence this presenter is not needed anymore and will also be destroyed
     * afterwards
     *
     */
    @MainThread
    protected abstract fun bindIntents()

    /**
     * This method will be called once the view has been detached permanently and hence the presenter
     * will be "destroyed" too. This is the correct time for doing some cleanup like unsubscribe from
     * RxSubscriptions, etc.
     *
     *
     *
     *
     * The counter part of this method is [.bindIntents] ()}.
     * This [.bindIntents] and [.unbindIntents] are kind of representing the
     * lifecycle of this Presenter.
     * [.bindIntents] is called the first time the view is attached
     * and [.unbindIntents] is called once the view is detached permanently because it has
     * been destroyed and hence this presenter is not needed anymore and will also be destroyed
     * afterwards
     *
     */
    protected fun unbindIntents() {}

    /**
     * This method creates a decorator around the original view's "intent". This method ensures that
     * no memory leak by using a [ViewIntentBinder] is caused by the subscription to the original
     * view's intent when the view gets detached.
     *
     *
     * Typically, this method is used in [.bindIntents] like this:
     * <pre>`
     * Observable<Boolean> loadIntent = intent(new ViewIntentBinder() {
     * @Override
     * public Observable<Boolean> bind(MyView view){
     * return view.loadIntent();
     * }
     * }
    </Boolean></Boolean>`</pre> *
     *
     * @param binder The [ViewIntentBinder] from where the the real view's intent will be
     * bound
     * @param <I>    The type of the intent
     * @return The decorated intent Observable emitting the intent
    </I> */
    @MainThread
    protected fun <I : Any> intent(binder: ViewIntentBinder<V, I>): Observable<I> {
        val intentRelay: Subject<I> = UnicastSubject.create()
        intentRelaysBinders.add(IntentRelayBinderPair(intentRelay, binder))
        return intentRelay
    }

    @MainThread
    private fun <I : Any> bindIntentActually(
        view: V,
        relayBinderPair: IntentRelayBinderPair<*>
    ): Observable<I> {
        if (view == null) {
            throw NullPointerException(
                "View is null. This is a Mosby internal bug. Please file an issue at https://github.com/sockeqwe/mosby/issues"
            )
        }
        if (relayBinderPair == null) {
            throw NullPointerException(
                "IntentRelayBinderPair is null. This is a Mosby internal bug. Please file an issue at https://github.com/sockeqwe/mosby/issues"
            )
        }
        val intentRelay = relayBinderPair.intentRelaySubject as Subject<I>
            ?: throw NullPointerException(
                "IntentRelay from binderPair is null. This is a Mosby internal bug. Please file an issue at https://github.com/sockeqwe/mosby/issues"
            )
        val intentBinder = relayBinderPair.intentBinder as ViewIntentBinder<V, I>
            ?: throw NullPointerException(
                ViewIntentBinder::class.java.simpleName
                        + " is null. This is a Mosby internal bug. Please file an issue at https://github.com/sockeqwe/mosby/issues"
            )
        val intent = intentBinder.bind(view)
            ?: throw NullPointerException(
                "Intent Observable returned from Binder $intentBinder is null"
            )
        if (intentDisposables == null) {
            intentDisposables = CompositeDisposable()
        }

        val observer = DisposableIntentObserver<I>(intentRelay)
        intentDisposables!!.add(intent.subscribeWith(observer))
        return intentRelay
    }
}