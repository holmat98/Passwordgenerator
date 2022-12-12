package com.mateuszholik.data.managers.io

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.assertj.core.api.Assertions.assertThat

class EncryptedSharedPrefManagerImplTest {

    private lateinit var context: Context
    private lateinit var encryptedSharedPreferences: SharedPreferences
    private lateinit var encryptedSharedPrefManager: EncryptedSharedPrefManagerImpl

    @BeforeEach
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        encryptedSharedPrefManager = EncryptedSharedPrefManagerImpl(context)

        encryptedSharedPreferences = EncryptedSharedPreferences.create(
            "EncryptedData",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Test
    fun dataIsCorrectlySavedToEncryptedSharedPreferences() {
        encryptedSharedPrefManager.write(KEY, DATA)

        val result = encryptedSharedPreferences.getString(KEY, "")

        assertThat(result).isEqualTo(DATA)
    }

    @Test
    fun dataIsCorrectlyReadedFromEncryptedSharedPreferences() {
        encryptedSharedPrefManager.write(KEY, DATA)

        val result = encryptedSharedPrefManager.readString(KEY)

        assertThat(result).isEqualTo(DATA)
    }

    @Test
    fun dataIsCorrectlyClearedInEncryptedSharedPreferences() {
        encryptedSharedPrefManager.write(KEY, DATA)
        encryptedSharedPrefManager.clear(KEY)

        val result = encryptedSharedPreferences.getString(KEY, "")

        assertThat(result).isEqualTo(DATA)
    }

    private companion object {
        const val KEY = "TEST_KEY"
        const val DATA = "TEST_DATA"
    }
}