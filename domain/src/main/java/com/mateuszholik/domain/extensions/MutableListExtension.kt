package com.mateuszholik.domain.extensions

import com.mateuszholik.domain.constants.Constants.SECURE_RANDOM_ALGORITHM
import java.security.SecureRandom

fun <T> MutableList<T>.getAndRemove(): T {
    val secureRandom = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM)
    val index = secureRandom.nextInt(this.size)
    val item = this[index]
    this.remove(item)

    return item
}