package com.aventurine.marvel

import androidx.annotation.NonNull
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.subjects.Subject

internal class DisposableIntentObserver<I : Any>(private val subject: Subject<I>) :
    DisposableObserver<I>() {

    override fun onNext(value: I) {
        subject.onNext(value)
    }

    override fun onError(e: Throwable) {
        throw IllegalStateException("View intents must not throw errors", e)
    }

    override fun onComplete() {
        subject.onComplete()
    }
}
