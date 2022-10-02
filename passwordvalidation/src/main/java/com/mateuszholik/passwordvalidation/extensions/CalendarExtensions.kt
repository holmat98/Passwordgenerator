package com.mateuszholik.passwordvalidation.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar

@SuppressLint("SimpleDateFormat")
internal fun Calendar.getTwoDigitYear(): Int =
    SimpleDateFormat("yy").format(this.time).toInt()
