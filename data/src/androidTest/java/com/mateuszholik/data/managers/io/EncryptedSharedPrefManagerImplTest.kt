package com.mateuszholik.data.managers.io

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mateuszholik.data.cryptography.Utils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EncryptedSharedPrefManagerImplTest {

    private lateinit var context: Context
    private lateinit var encryptedSharedPreferences: SharedPreferences
    private lateinit var encryptedSharedPrefManager: EncryptedSharedPrefManagerImpl

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        encryptedSharedPrefManager = EncryptedSharedPrefManagerImpl(context)

        encryptedSharedPreferences = EncryptedSharedPreferences.create(
            "EncryptedData",
            MasterKeys.getOrCreate(Utils.KEY_GEN_PARAMETER_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Test
    fun dataIsCorrectlySavedToEncryptedSharedPreferences() {
        encryptedSharedPrefManager.write(KEY, DATA)

        val result = encryptedSharedPreferences.getString(KEY, "")

        Assert.assertEquals(DATA, result)
    }

    @Test
    fun dataIsCorrectlyReadedFromEncryptedSharedPreferences() {
        encryptedSharedPrefManager.write(KEY, DATA)

        val result = encryptedSharedPrefManager.readString(KEY)

        Assert.assertEquals(DATA, result)
    }

    @Test
    fun dataIsCorrectlyClearedInEncryptedSharedPreferences() {
        encryptedSharedPrefManager.write(KEY, DATA)
        encryptedSharedPrefManager.clear(KEY)

        val result = encryptedSharedPreferences.getString(KEY, "")

        Assert.assertEquals("", result)
    }

    private companion object {
        const val KEY = "TEST_KEY"
        const val DATA = "TEST_DATA"
    }
}