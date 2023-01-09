package com.mateuszholik.passwordgenerator.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T> MediatorLiveData<T>.addSources(
    vararg sources: LiveData<out Any>,
    valueAfterChange: () -> T
): LiveData<T> {
    sources.forEach {
        this.addSource(it) {
            value = valueAfterChange()
        }
    }

    return this
}