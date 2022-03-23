package com.mateuszholik.data.extensions

import com.mateuszholik.data.managers.io.SharedPrefManager

inline fun <reified T> SharedPrefManager.read(key: String) = this.read(key, T::class.java)