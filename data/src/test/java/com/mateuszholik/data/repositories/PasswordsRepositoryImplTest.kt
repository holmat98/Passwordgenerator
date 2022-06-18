package com.mateuszholik.data.repositories

import com.mateuszholik.cryptography.EncryptionManager
import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.daos.PasswordsDao
import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.data.mappers.PasswordListMapper
import com.mateuszholik.data.repositories.models.Password
import io.mockk.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDateTime

@RunWith(JUnit4::class)
class PasswordsRepositoryImplTest {

    private val passwordsDao = mockk<PasswordsDao> {
        every { insert(PASSWORD_DB) } returns Completable.complete()
        every { update(PASSWORD_DB) } returns Completable.complete()
        every { deletePassword(MAPPED_PASSWORD.id) } returns Completable.complete()
        every { getAllPasswords() } returns Single.just(listOf(PASSWORD_DB))
    }
    private val passwordListMapper = mockk<PasswordListMapper> {
        every { map(listOf(PASSWORD_DB)) } returns listOf(MAPPED_PASSWORD)
    }
    private val encryptionManager = mockk<EncryptionManager> {
        every { encrypt(PLATFORM_NAME) } returns ENCRYPTED_PLATFORM
        every { encrypt(PASSWORD) } returns ENCRYPTED_PASSWORD
    }
    private val sharedPrefManager = mockk<SharedPrefManager> {
        every { readLong(any()) } returns PASSWORD_VALIDITY_IN_DAYS
    }

    private val passwordsRepository = PasswordsRepositoryImpl(
        passwordsDao,
        passwordListMapper,
        encryptionManager,
        sharedPrefManager
    )

    @Before
    fun setUp() {
        mockkStatic(LocalDateTime.now()::class)
        every { LocalDateTime.now() } returns TODAY_DATE
    }

    @After
    fun tearDown() {
        unmockkStatic(LocalDateTime::class)
    }

    @Test
    fun `Password is saved correctly to the database`() {
        passwordsRepository.insert(PLATFORM_NAME, PASSWORD)
            .test()
            .assertComplete()

        verify(exactly = 1) { passwordsDao.insert(PASSWORD_DB) }
    }

    @Test
    fun `Password is updated correctly in the database`() {
        passwordsRepository.update(MAPPED_PASSWORD)
            .test()
            .assertComplete()

        verify(exactly = 1) { passwordsDao.update(PASSWORD_DB) }
    }

    @Test
    fun `Password is deleted correctly`() {
        passwordsRepository.delete(MAPPED_PASSWORD.id)
            .test()
            .assertComplete()

        verify(exactly = 1) {
            passwordsDao.deletePassword(MAPPED_PASSWORD.id)
        }
    }

    @Test
    fun `All passwords are correctly provided and mapped to list of Password objects`() {
        passwordsRepository.getAllPasswords()
            .test()
            .assertValue(listOf(MAPPED_PASSWORD))

        verify(exactly = 1) {
            passwordsDao.getAllPasswords()
        }
    }

    private companion object {
        const val ID = 0L
        const val PLATFORM_NAME = "platform"
        const val PASSWORD = "password"
        val ENCRYPTED_PLATFORM = EncryptedData(
            iv = ByteArray(10),
            data = ByteArray(11)
        )
        val ENCRYPTED_PASSWORD = EncryptedData(
            iv = ByteArray(12),
            data = ByteArray(13)
        )
        val TODAY_DATE: LocalDateTime = LocalDateTime.of(2022, 6, 11, 12, 0, 0)
        const val PASSWORD_VALIDITY_IN_DAYS = 90L
        val PASSWORD_DB = PasswordDB(
            id = ID,
            platformName = ENCRYPTED_PLATFORM.data,
            platformIV = ENCRYPTED_PLATFORM.iv,
            password = ENCRYPTED_PASSWORD.data,
            passwordIV = ENCRYPTED_PASSWORD.iv,
            expiringDate = TODAY_DATE.plusDays(PASSWORD_VALIDITY_IN_DAYS)
        )
        val MAPPED_PASSWORD = Password(
            id = ID,
            platformName = PLATFORM_NAME,
            password = PASSWORD,
            expiringDate = TODAY_DATE.plusDays(PASSWORD_VALIDITY_IN_DAYS)
        )
    }
}