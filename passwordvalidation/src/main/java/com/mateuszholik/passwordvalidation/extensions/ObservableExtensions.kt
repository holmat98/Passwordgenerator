package com.mateuszholik.passwordvalidation.extensions

import io.reactivex.rxjava3.core.Observable

inline fun <reified T: Any> T.asObservable(): Observable<T> = Observable.just(this)
