package com.mateuszholik.data.managers.io

import android.content.SharedPreferences

abstract class SharedPrefManager {

    protected abstract val sharedPreferences: SharedPreferences

    fun <T> write(key: String, data: T) {
        val edit = sharedPreferences.edit()
        when (data) {
            is String -> edit.putString(key, data)
            is Boolean -> edit.putBoolean(key, data)
            is Long -> edit.putLong(key, data)
            is Float -> edit.putFloat(key, data)
            is Int -> edit.putInt(key, data)
        }
        edit.apply()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> read(key: String, kClass: Class<T>): T? =
        when {
            kClass.isAssignableFrom(String::class.java) -> sharedPreferences.getString(key, "") as? T
            kClass.isAssignableFrom(Boolean::class.java) -> sharedPreferences.getBoolean(key, false) as? T
            kClass.isAssignableFrom(Long::class.java) -> sharedPreferences.getLong(key, 0L) as? T
            kClass.isAssignableFrom(Float::class.java) -> sharedPreferences.getFloat(key, 0f) as? T
            kClass.isAssignableFrom(Int::class.java) -> sharedPreferences.getInt(key, 0) as? T
            else -> {
                sharedPreferences.getString(key, "") as? T
            }
        }

    fun clear(key: String) {
        sharedPreferences
            .edit()
            .remove(key)
            .apply()
    }
}