package com.mateuszholik.data.mappers

import com.mateuszholik.cryptography.KeyBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.models.views.AutofillPasswordDetailsView
import com.mateuszholik.data.repositories.models.AutofillPasswordDetails
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AutofillPasswordDetailsViewMapperImplTest {

    private val encryptionManager = mockk<KeyBaseEncryptionManager>()

    private val autofillPasswordDetailsViewMapper = AutofillPasswordDetailsViewMapperImpl(
        encryptionManager = encryptionManager
    )

    @Test
    fun `When map is used then AutofillPasswordDetailsView is correctly mapped to the AutofillPasswordDetails`() {
        every {
            encryptionManager.decrypt(ENCRYPTED_PASSWORD)
        } returns PASSWORD

        every {
            encryptionManager.decrypt(ENCRYPTED_PLATFORM_NAME)
        } returns PLATFORM_NAME

        every {
            encryptionManager.decrypt(ENCRYPTED_PACKAGE_NAME)
        } returns PACKAGE_NAME

        val autofillPasswordDetailsView = AutofillPasswordDetailsView(
            id = 1,
            platformName = ENCRYPTED_PLATFORM_NAME.data,
            platformNameIv = ENCRYPTED_PLATFORM_NAME.iv,
            password = ENCRYPTED_PASSWORD.data,
            passwordIv = ENCRYPTED_PASSWORD.iv,
            packageName = ENCRYPTED_PACKAGE_NAME.data,
            packageNameIv = ENCRYPTED_PACKAGE_NAME.iv
        )

        val result = autofillPasswordDetailsViewMapper.map(autofillPasswordDetailsView)

        assertThat(result).isEqualTo(
            AutofillPasswordDetails(
                id = 1,
                platformName = PLATFORM_NAME,
                password = PASSWORD,
                packageName = PACKAGE_NAME
            )
        )
    }

    @Test
    fun `When packageName is null and map is used then AutofillPasswordDetailsView is correctly mapped to the AutofillPasswordDetails`() {
        every {
            encryptionManager.decrypt(ENCRYPTED_PASSWORD)
        } returns PASSWORD

        every {
            encryptionManager.decrypt(ENCRYPTED_PLATFORM_NAME)
        } returns PLATFORM_NAME

        val autofillPasswordDetailsView = AutofillPasswordDetailsView(
            id = 1,
            platformName = ENCRYPTED_PLATFORM_NAME.data,
            platformNameIv = ENCRYPTED_PLATFORM_NAME.iv,
            password = ENCRYPTED_PASSWORD.data,
            passwordIv = ENCRYPTED_PASSWORD.iv,
            packageName = null,
            packageNameIv = null
        )

        val result = autofillPasswordDetailsViewMapper.map(autofillPasswordDetailsView)

        assertThat(result).isEqualTo(
            AutofillPasswordDetails(
                id = 1,
                platformName = PLATFORM_NAME,
                password = PASSWORD,
                packageName = null
            )
        )
    }

    private companion object {
        const val PLATFORM_NAME = "platform"
        const val PASSWORD = "password"
        const val PACKAGE_NAME = "com.example.package"
        val ENCRYPTED_PASSWORD = EncryptedData(
            data = ByteArray(5) { it.toByte() },
            iv = ByteArray(6) { it.toByte() }
        )
        val ENCRYPTED_PLATFORM_NAME = EncryptedData(
            data = ByteArray(7) { it.toByte() },
            iv = ByteArray(8) { it.toByte() }
        )
        val ENCRYPTED_PACKAGE_NAME = EncryptedData(
            data = ByteArray(9) { it.toByte() },
            iv = ByteArray(10) { it.toByte() }
        )
    }
}
