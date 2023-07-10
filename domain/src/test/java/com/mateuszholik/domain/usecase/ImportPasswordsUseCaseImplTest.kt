package com.mateuszholik.domain.usecase

import android.net.Uri
import com.mateuszholik.cryptography.PasswordBaseEncryptionManager
import com.mateuszholik.cryptography.extensions.toEncryptedData
import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.data.repositories.models.NewPassword
import com.mateuszholik.domain.mappers.ExportedPasswordsListToNewPasswordsListMapper
import com.mateuszholik.domain.models.ExportedPassword
import com.mateuszholik.domain.models.ImportType
import com.mateuszholik.domain.parsers.PasswordsParser
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import org.junit.jupiter.api.Test

internal class ImportPasswordsUseCaseImplTest {

    private val readDataFromFileUseCase = mockk<ReadDataFromFileUseCase>()
    private val encryptionManager = mockk<PasswordBaseEncryptionManager>()
    private val passwordsParser = mockk<PasswordsParser> {
        every { parseFromString(NOT_ENCRYPTED_DATA_FROM_FILE) } returns listOf(EXPORTED_PASSWORD)
    }
    private val exportedPasswordsMapper = mockk<ExportedPasswordsListToNewPasswordsListMapper> {
        every { map(listOf(EXPORTED_PASSWORD)) } returns listOf(NEW_PASSWORD)
    }
    private val passwordsRepository = mockk<PasswordsRepository> {
        every { insertPasswords(listOf(NEW_PASSWORD)) } returns Completable.complete()
    }

    private val importPasswordsUseCase = ImportPasswordsUseCaseImpl(
        readDataFromFileUseCase = readDataFromFileUseCase,
        encryptionManager = encryptionManager,
        passwordsParser = passwordsParser,
        exportedPasswordsMapper = exportedPasswordsMapper,
        passwordsRepository = passwordsRepository
    )

    @Test
    fun `Passwords are imported from file and saved and are not decrypted`() {
        val input = ImportType.Import(URI)

        every { readDataFromFileUseCase(URI) } returns Maybe.just(NOT_ENCRYPTED_DATA_FROM_FILE)

        importPasswordsUseCase(input)
            .test()
            .assertComplete()

        verify(exactly = 1) { readDataFromFileUseCase(URI) }
        verify(exactly = 0) { encryptionManager.encrypt(any(), any()) }
        verify(exactly = 1) { passwordsParser.parseFromString(NOT_ENCRYPTED_DATA_FROM_FILE) }
        verify(exactly = 1) { exportedPasswordsMapper.map(listOf(EXPORTED_PASSWORD)) }
        verify(exactly = 1) { passwordsRepository.insertPasswords(listOf(NEW_PASSWORD)) }
    }

    @Test
    fun `Passwords are imported from file and saved and are decrypted`() {
        val input = ImportType.EncryptedImport(URI, ENCRYPTION_PASSWORD)

        every { readDataFromFileUseCase(URI) } returns Maybe.just(ENCRYPTED_DATA_FROM_FILE)

        every {
            encryptionManager.decrypt(
                ENCRYPTION_PASSWORD,
                ENCRYPTED_DATA_FROM_FILE.toEncryptedData()
            )
        } returns DECRYPTED_PASSWORDS

        importPasswordsUseCase(input)
            .test()
            .assertComplete()

        verify(exactly = 1) { readDataFromFileUseCase(URI) }
        verify(exactly = 1) {
            encryptionManager.decrypt(
                ENCRYPTION_PASSWORD,
                ENCRYPTED_DATA_FROM_FILE.toEncryptedData()
            )
        }
        verify(exactly = 1) { passwordsParser.parseFromString(NOT_ENCRYPTED_DATA_FROM_FILE) }
        verify(exactly = 1) { exportedPasswordsMapper.map(listOf(EXPORTED_PASSWORD)) }
        verify(exactly = 1) { passwordsRepository.insertPasswords(listOf(NEW_PASSWORD)) }
    }

    private companion object {
        const val ENCRYPTION_PASSWORD = "1234abcd"
        const val PASSWORD = "password"
        const val PLATFORM_NAME = "platformName"
        const val ENCRYPTED_DATA_FROM_FILE =
            "11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26\n31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46"
        const val NOT_ENCRYPTED_DATA_FROM_FILE = "$PLATFORM_NAME:$PASSWORD"
        const val DECRYPTED_PASSWORDS = NOT_ENCRYPTED_DATA_FROM_FILE
        val NEW_PASSWORD = NewPassword(
            platformName = PLATFORM_NAME,
            password = PASSWORD
        )
        val EXPORTED_PASSWORD = ExportedPassword(
            platformName = PLATFORM_NAME,
            password = PASSWORD
        )
        val URI = mockk<Uri>()
    }
}
