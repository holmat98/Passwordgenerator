package com.mateuszholik.passwordgenerator.extensions

import java.time.LocalDateTime
import java.time.ZoneOffset

fun LocalDateTime.getDiffFromNowInMilliseconds() =
    this.toInstant(ZoneOffset.UTC).toEpochMilli() -
            LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()