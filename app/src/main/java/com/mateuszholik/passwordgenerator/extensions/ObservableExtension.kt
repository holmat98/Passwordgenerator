package com.mateuszholik.passwordgenerator.extensions

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T: Any> Single<T>.subscribeWithObserveOnMainThread(
    scheduler: Scheduler = Schedulers.io(),
    doOnSuccess: (T) -> Unit,
    doOnError: (Throwable) -> Unit
): Disposable = this.subscribeOn(scheduler)
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(
        { doOnSuccess(it) },
        { doOnError(it) }
    )

fun <T: Any> Maybe<T>.subscribeWithObserveOnMainThread(
    scheduler: Scheduler = Schedulers.io(),
    doOnSuccess: (T) -> Unit,
    doOnError: (Throwable) -> Unit
): Disposable = this.subscribeOn(scheduler)
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(
        { doOnSuccess(it) },
        { doOnError(it) }
    )

fun <T: Any> Observable<T>.subscribeWithObserveOnMainThread(
    scheduler: Scheduler = Schedulers.io(),
    doOnNext: (T) -> Unit,
    doOnSuccess: (T) -> Unit,
    doOnError: (Throwable) -> Unit
): Disposable = this.subscribeOn(scheduler)
    .observeOn(AndroidSchedulers.mainThread())
    .doOnNext { doOnNext(it) }
    .subscribe(
        { doOnSuccess(it) },
        { doOnError(it) }
    )

fun Completable.subscribeWithObserveOnMainThread(
    scheduler: Scheduler = Schedulers.io(),
    doOnSuccess: () -> Unit = {},
    doOnError: (Throwable) -> Unit
): Disposable = this.subscribeOn(scheduler)
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(
        { doOnSuccess() },
        { doOnError(it) }
    )
