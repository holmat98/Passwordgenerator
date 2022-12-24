package com.mateuszholik.data.repositories

import com.mateuszholik.cryptography.models.EncryptedData
import com.mateuszholik.data.db.daos.PasswordsDao
import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.mappers.NewPasswordToPasswordDBMapper
import com.mateuszholik.data.mappers.PasswordDBListToPasswordListMapper
import com.mateuszholik.data.mappers.PasswordDBToPasswordMapper
import com.mateuszholik.data.mappers.UpdatedPasswordToPasswordDBMapper
import com.mateuszholik.data.repositories.models.NewPassword
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.data.repositories.models.UpdatedPassword
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class PasswordsRepositoryImplTest {

    private val passwordsDao = mockk<PasswordsDao> {
        every { insertAndGetId(PASSWORD_DB) } returns Single.just(ID)
        every { update(PASSWORD_DB) } returns Completable.complete()
        every { deletePassword(MAPPED_PASSWORD.id) } returns Completable.complete()
        every { getAllPasswords() } returns Single.just(listOf(PASSWORD_DB))
    }

    private val passwordDBListToPasswordListMapper = mockk<PasswordDBListToPasswordListMapper> {
        every { map(listOf(PASSWORD_DB)) } returns listOf(MAPPED_PASSWORD)
    }

    private val passwordDBToPasswordMapper = mockk<PasswordDBToPasswordMapper> {
        every { map(PASSWORD_DB) } returns MAPPED_PASSWORD
    }

    private val newPasswordToPasswordDBMapper = mockk<NewPasswordToPasswordDBMapper> {
        every { map(NEW_PASSWORD) } returns Single.just(PASSWORD_DB)
    }

    private val updatedPasswordToPasswordDBMapper = mockk<UpdatedPasswordToPasswordDBMapper> {
        every { map(UPDATED_PASSWORD) } returns Single.just(PASSWORD_DB)
    }


    private val passwordsRepository = PasswordsRepositoryImpl(
        passwordsDao = passwordsDao,
        passwordDBListToPasswordListMapper = passwordDBListToPasswordListMapper,
        passwordDBToPasswordMapper = passwordDBToPasswordMapper,
        newPasswordToPasswordDBMapper = newPasswordToPasswordDBMapper,
        updatedPasswordToPasswordDBMapper = updatedPasswordToPasswordDBMapper
    )

    @Test
    fun `Password is saved correctly to the database and its id is returned`() {
        passwordsRepository.createAndGetId(NEW_PASSWORD)
            .test()
            .assertValue(ID)

        verify(exactly = 1) { passwordsDao.insertAndGetId(PASSWORD_DB) }
    }

    @Test
    fun `Password is updated correctly in the database`() {
        passwordsRepository.update(UPDATED_PASSWORD)
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
        const val ID = 1L
        const val PLATFORM_NAME = "platform"
        const val PASSWORD = "password"
        val NEW_PASSWORD = NewPassword(
            platformName = PLATFORM_NAME,
            password = PASSWORD
        )
        val UPDATED_PASSWORD = UpdatedPassword(
            id = ID,
            platformName = PLATFORM_NAME,
            password = PASSWORD
        )
        val ENCRYPTED_PLATFORM = EncryptedData(
            iv = ByteArray(10),
            data = ByteArray(11)
        )
        val ENCRYPTED_PASSWORD = EncryptedData(
            iv = ByteArray(12),
            data = ByteArray(13)
        )
        val EXPIRING_DATE: LocalDateTime = LocalDateTime.of(2022, 6, 11, 12, 0, 0)
        val PASSWORD_DB = PasswordDB(
            id = ID,
            platformName = ENCRYPTED_PLATFORM.data,
            platformIV = ENCRYPTED_PLATFORM.iv,
            password = ENCRYPTED_PASSWORD.data,
            passwordIV = ENCRYPTED_PASSWORD.iv,
            expiringDate = EXPIRING_DATE
        )
        val MAPPED_PASSWORD = Password(
            id = ID,
            platformName = PLATFORM_NAME,
            password = PASSWORD,
            expiringDate = EXPIRING_DATE
        )
    }
}