package com.mateuszholik.domain.extensions

import com.mateuszholik.domain.utils.Constants
import java.security.SecureRandom

internal fun String.getRandom(): String {
    val secureRandom = SecureRandom.getInstance(Constants.SECURE_RANDOM_ALGORITHM)
    val index = secureRandom.nextInt(this.length)

    return "${this[index]}"
}