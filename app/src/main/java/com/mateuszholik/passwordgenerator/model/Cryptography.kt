package com.mateuszholik.passwordgenerator.model

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

object Cryptography {
    private val keyName: String by lazy {
        "savedPasswordsKey"
    }

    fun isKeyGenerated() : Boolean{
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)

        val secretKeyEntry = keyStore.getEntry(keyName, null) as KeyStore.SecretKeyEntry

        val secretKey = secretKeyEntry.secretKey

        return secretKey != null
    }

    fun generateKey(){
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            keyName,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    fun getKey() : SecretKey{
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)

        val secretKeyEntry = keyStore.getEntry(keyName, null) as KeyStore.SecretKeyEntry

        return secretKeyEntry.secretKey
    }

    fun encryptData(text: String): Pair<ByteArray, ByteArray>{
        val cipher = Cipher.getInstance("AES/CBC/NoPadding")

        var temp: String = text
        while (temp.toByteArray().size % 16 != 0){
            temp += "\u0020"
        }

        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        val iv = cipher.iv
        val encryptedText = cipher.doFinal(temp.toByteArray(Charsets.UTF_8))

        return Pair(iv, encryptedText)
    }

    fun decryptData(iv: ByteArray, encryptedData: ByteArray): String{
        val cipher = Cipher.getInstance("AES/CBC/NoPadding")
        val spec = IvParameterSpec(iv)

        cipher.init(Cipher.DECRYPT_MODE, getKey(), spec)

        return cipher.doFinal(encryptedData).toString(Charsets.UTF_8).trim()
    }
}