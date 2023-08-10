package com.mateuszholik.passwordgenerator.extensions

/** Returns the boolean if it is not `null`, or the false otherwise. */
internal fun Boolean?.orFalse(): Boolean = this ?: false
