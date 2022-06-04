package com.mateuszholik.data.managers.io

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.mateuszholik.data.cryptography.Utils.KEY_GEN_PARAMETER_SPEC

abstract class EncryptedSharedPrefManager : SharedPrefManager()

internal class EncryptedSharedPrefManagerImpl(context: Context) : EncryptedSharedPrefManager() {

    override val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            ENCRYPTED_SHARED_PREF_FILE_NAME,
            MasterKeys.getOrCreate(KEY_GEN_PARAMETER_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    private companion object {
        const val ENCRYPTED_SHARED_PREF_FILE_NAME = "EncryptedData"
    }
}