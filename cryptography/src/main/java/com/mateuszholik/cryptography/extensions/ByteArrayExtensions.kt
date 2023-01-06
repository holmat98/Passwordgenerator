package com.mateuszholik.cryptography.extensions

internal fun ByteArray.convertToString(): String =
    this.joinToString(" ").trim()