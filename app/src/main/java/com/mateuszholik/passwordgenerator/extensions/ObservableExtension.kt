package com.mateuszholik.passwordgenerator.extensions

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Single<T>.subscribeWithObserveOnMainThread(
    scheduler: Scheduler = Schedulers.io(),
    doOnSuccess: (T) -> Unit,
    doOnError: (Throwable) -> Unit
) = this.subscribeOn(scheduler)
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(
        { doOnSuccess(it) },
        { doOnError(it) }
    )

fun Completable.subscribeWithObserveOnMainThread(
    scheduler: Scheduler = Schedulers.io(),
    doOnSuccess: () -> Unit,
    doOnError: (Throwable) -> Unit
) = this.subscribeOn(scheduler)
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(
        { doOnSuccess() },
        { doOnError(it) }
    )