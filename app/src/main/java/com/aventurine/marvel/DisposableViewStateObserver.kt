package com.aventurine.marvel

import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.subjects.BehaviorSubject


internal class DisposableViewStateObserver<VS : Any>(private val subject: BehaviorSubject<VS>) :
    DisposableObserver<VS>() {
    override fun onNext(value: VS) {
        subject.onNext(value)
    }

    override fun onError(e: Throwable) {
        throw IllegalStateException(
            "ViewState observable must not reach error state - onError()", e
        )
    }

    override fun onComplete() {
        // ViewState observable never completes so ignore any complete event
    }
}
