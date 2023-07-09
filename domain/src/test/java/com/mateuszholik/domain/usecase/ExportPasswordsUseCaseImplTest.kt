package com.mateuszholik.domain.usecase

import android.net.Uri
import com.mateuszholik.cryptography.PasswordBaseEncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.repositories.OldPasswordsRepository
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.factories.UriFactory
import com.mateuszholik.domain.mappers.PasswordsListToExportPasswordsListMapper
import com.mateuszholik.domain.models.DataToSave
import com.mateuszholik.domain.models.ExportType
import com.mateuszholik.domain.models.ExportedPassword
import com.mateuszholik.domain.parsers.PasswordsParser
import io.mockk.mockkStatic
import io.mockk.mockk
import io.mockk.unmockkStatic
import io.mockk.every
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class ExportPasswordsUseCaseImplTest {

    private val oldPasswordsRepository = mockk<OldPasswordsRepository> {
        every { getAllPasswords() } returns Single.just(listOf(PASSWORD))
    }
    private val passwordsListToExportPasswordsListMapper =
        mockk<PasswordsListToExportPasswordsListMapper> {
            every { map(listOf(PASSWORD)) } returns listOf(EXPORTED_PASSWORD)
        }
    private val passwordsParser = mockk<PasswordsParser> {
        every { parseToString(listOf(EXPORTED_PASSWORD)) } returns PARSED_PASSWORDS
    }
    private val encryptionManager = mockk<PasswordBaseEncryptionManager>()
    private val saveDataToFileUseCase = mockk<SaveDataToFileUseCase>()
    private val uriFactory = mockk<UriFactory> {
        every { create(any(), any()) } returns URI
    }

    @BeforeEach
    fun setUp() {
        mockkStatic(LocalDateTime.now()::class)
        every { LocalDateTime.now() } returns TODAY
    }

    @AfterEach

    fun tearDown() {
        unmockkStatic(LocalDateTime.now()::class)
    }

    private val exportPasswordsUseCase = ExportPasswordsUseCaseImpl(
        oldPasswordsRepository = oldPasswordsRepository,
        passwordsListToExportPasswordsListMapper = passwordsListToExportPasswordsListMapper,
        passwordsParser = passwordsParser,
        encryptionManager = encryptionManager,
        saveDataToFileUseCase = saveDataToFileUseCase,
        uriFactory = uriFactory
    )

    @Test
    fun `All passwords are saved to file and are not encrypted`() {
        val input = ExportType.Export
        val dataToSave = DataToSave(
            data = PARSED_PASSWORDS,
            uri = URI
        )
        every {
            saveDataToFileUseCase(dataToSave)
        } returns Completable.complete()

        exportPasswordsUseCase(input)
            .test()
            .assertComplete()

        verify(exactly = 1) { oldPasswordsRepository.getAllPasswords() }
        verify(exactly = 1) { passwordsListToExportPasswordsListMapper.map(listOf(PASSWORD)) }
        verify(exactly = 1) { passwordsParser.parseToString(listOf(EXPORTED_PASSWORD)) }
        verify(exactly = 0) { encryptionManager.encrypt(any(), any()) }
        verify(exactly = 1) { saveDataToFileUseCase.invoke(dataToSave) }
    }

    @Test
    fun `All passwords are saved to file and are encrypted`() {
        val input = ExportType.EncryptedExport(ENCRYPTION_PASSWORD)
        val dataToSave = DataToSave(
            data = ENCRYPTED_DATA.toString(),
            uri = URI
        )

        every {
            encryptionManager.encrypt(ENCRYPTION_PASSWORD, PARSED_PASSWORDS)
        } returns ENCRYPTED_DATA

        every {
            saveDataToFileUseCase(dataToSave)
        } returns Completable.complete()

        exportPasswordsUseCase(input)
            .test()
            .assertComplete()

        verify(exactly = 1) { oldPasswordsRepository.getAllPasswords() }
        verify(exactly = 1) { passwordsListToExportPasswordsListMapper.map(listOf(PASSWORD)) }
        verify(exactly = 1) { passwordsParser.parseToString(listOf(EXPORTED_PASSWORD)) }
        verify(exactly = 1) { encryptionManager.encrypt(ENCRYPTION_PASSWORD, PARSED_PASSWORDS) }
        verify(exactly = 1) { saveDataToFileUseCase.invoke(dataToSave) }
    }

    private companion object {
        const val ENCRYPTION_PASSWORD = "1234abcd"
        val TODAY: LocalDateTime = LocalDateTime.of(2023, 1, 7, 12, 0, 0)
        const val PASSWORD_STRING = "password"
        const val PLATFORM_NAME = "platformName"
        const val PARSED_PASSWORDS = "$PLATFORM_NAME:$PASSWORD_STRING"
        val PASSWORD = Password(
            id = 1L,
            platformName = PLATFORM_NAME,
            password = PASSWORD_STRING,
            expiringDate = LocalDateTime.of(2023, 1, 7, 12, 0, 0)
        )
        val EXPORTED_PASSWORD = ExportedPassword(
            platformName = PLATFORM_NAME,
            password = PASSWORD_STRING
        )
        val ENCRYPTED_DATA = EncryptedData(
            data = PARSED_PASSWORDS.toByteArray(),
            iv = ByteArray(16)
        )
        val URI = mockk<Uri>()
    }
}
