package com.mateuszholik.domain.extensions

import com.mateuszholik.domain.utils.Constants.SECURE_RANDOM_ALGORITHM
import java.security.SecureRandom

internal fun <T> MutableList<T>.getRandomAndRemove(): T {
    val secureRandom = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM)
    val index = secureRandom.nextInt(this.size)
    val item = this[index]
    this.remove(item)

    return item
}