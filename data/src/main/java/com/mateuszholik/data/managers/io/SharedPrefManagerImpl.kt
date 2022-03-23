package com.mateuszholik.data.managers.io

import android.content.Context
import android.content.SharedPreferences

internal class SharedPrefManagerImpl(
    private val context: Context
) : SharedPrefManager() {

    override val sharedPreferences: SharedPreferences
        get() = context.getSharedPreferences(
            SHARED_PREF_FILE_NAME,
            Context.MODE_PRIVATE
        )

    private companion object {
        const val SHARED_PREF_FILE_NAME = "sharedPref"
    }
}