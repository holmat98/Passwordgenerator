package com.mateuszholik.data.managers.io

import android.content.SharedPreferences
import com.mateuszholik.data.utils.Constants.EMPTY_STRING

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

    fun readString(key: String): String? = sharedPreferences.getString(key, EMPTY_STRING)

    fun readLong(key: String, defValue: Long = 0): Long = sharedPreferences.getLong(key, defValue)

    fun readBoolean(key: String): Boolean = sharedPreferences.getBoolean(key, false)

    fun clear(key: String) {
        sharedPreferences
            .edit()
            .remove(key)
            .apply()
    }
}